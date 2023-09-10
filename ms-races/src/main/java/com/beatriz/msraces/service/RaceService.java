package com.beatriz.msraces.service;

import com.beatriz.msraces.client.CarFeignClient;
import com.beatriz.msraces.dto.CarDtoResponse;
import com.beatriz.msraces.dto.RaceDtoRequest;
import com.beatriz.msraces.dto.RaceDtoResponse;
import com.beatriz.msraces.entity.Race;
import com.beatriz.msraces.exception.CarNotFoundException;
import com.beatriz.msraces.exception.IdNotFoundException;
import com.beatriz.msraces.exception.InvalidActionException;
import com.beatriz.msraces.exception.InvalidOvertakeException;
import com.beatriz.msraces.repository.RaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RaceService {
    private RaceRepository raceRepository;
    private ModelMapper mapper;
    private CarFeignClient carFeignClient;


    public RaceService(RaceRepository raceRepository, ModelMapper mapper, CarFeignClient carFeignClient) {
        this.raceRepository = raceRepository;
        this.mapper = mapper;
        this.carFeignClient = carFeignClient;
    }


    private RaceDtoResponse mapToDTO(Race race){
        RaceDtoResponse raceDtoResponse = mapper.map(race, RaceDtoResponse.class);
        return raceDtoResponse;
    }

    public RaceDtoResponse createRace(RaceDtoRequest raceDtoRequest) {
        List<CarDtoResponse> randomCars = getRandomCarsForRace();

        Race race = mapper.map(raceDtoRequest, Race.class);

        race.setCars(randomCars);
        race.setStatus("RACE STARTED!");
        Race newRace = raceRepository.save(race);
        return mapper.map(newRace, RaceDtoResponse.class);
    }


    public Race getRaceById(String id) {
        Race race = raceRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Id not found"));
        return race;
    }

    public List<RaceDtoResponse> findAll() {
        List<Race> races = raceRepository.findAll();
        return races.stream()
                .map(car -> mapper.map(car, RaceDtoResponse.class))
                .collect(Collectors.toList());
    }

    public RaceDtoResponse updateRace(RaceDtoRequest raceDtoRequest, String id) {

        Race race = raceRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Id not found"));

        if(race.getStatus().equals("RACE FINISHED!")){
            throw new InvalidActionException("You can't update the race because it has already ended");
        }else {
            race.setName(raceDtoRequest.getName());
            race.setCountry(raceDtoRequest.getCountry());
            race.setDate(raceDtoRequest.getDate());

            Race updatedRace = raceRepository.save(race);
            return mapToDTO(updatedRace);
        }
    }

    public void deleteRaceById(String id) {
        Race race = raceRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Id not found"));
        raceRepository.delete(race);
    }

    public List<CarDtoResponse> getRandomCarsForRace() {
        List<CarDtoResponse> allCars = carFeignClient.getAllCars();
        Collections.shuffle(allCars);
        int numCarsToSelect = new Random().nextInt(8) + 3;
        List<CarDtoResponse> selectedCars = allCars.subList(0, numCarsToSelect);

        return selectedCars;
    }

    public RaceDtoResponse overtakeCar(String raceId, String carId, String carToOvertakeId) {
        Race race = raceRepository.findById(raceId).orElseThrow(() -> new IdNotFoundException("Race not found"));

        if(race.getStatus().equals("RACE FINISHED!")){
            throw new InvalidActionException("You can't update the race because it has already ended");
        }else{
            CarDtoResponse car = null;
            CarDtoResponse carToOvertake = null;

            for (CarDtoResponse c : race.getCars()) {
                if (c.getId().equals(carId)) {
                    car = c;
                }
                if (c.getId().equals(carToOvertakeId)) {
                    carToOvertake = c;
                }
            }

            if (car == null || carToOvertake == null) {
                throw new IdNotFoundException("Car or carToOvertake not found in the race");
            }

            int carIndex = race.getCars().indexOf(car);
            int carToOvertakeIndex = race.getCars().indexOf(carToOvertake);

            if (carIndex > carToOvertakeIndex) {
                race.getCars().remove(car);
                race.getCars().add(carToOvertakeIndex, car);
            } else {
                throw new RuntimeException("Car cannot overtake carToOvertake");
            }

            raceRepository.save(race);

            return mapToDTO(race);
        }
    }

    public Race finishRace(String id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Id not found"));

        race.setStatus("RACE FINISHED!");
        raceRepository.save(race);
        return race;
    }

}
