package com.beatriz.msraces.service;

import com.beatriz.msraces.client.CarFeignClient;
import com.beatriz.msraces.dto.RaceDtoRequest;
import com.beatriz.msraces.dto.RaceDtoResponse;
import com.beatriz.msraces.entity.Race;
import com.beatriz.msraces.exception.IdNotFoundException;
import com.beatriz.msraces.repository.RaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Race race = mapper.map(raceDtoRequest, Race.class);
        Race newRace = raceRepository.save(race);
        return mapper.map(newRace, RaceDtoResponse.class);
    }

    public RaceDtoResponse getRaceById(String id) {
        Race race = raceRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Id not found"));
        return mapToDTO(race);
    }

    public List<Race> findAll() {
        return raceRepository.findAll();
    }

    public RaceDtoResponse updateRace(RaceDtoRequest raceDtoRequest, String id) {
        Race race = raceRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Id not found"));

        race.setName(raceDtoRequest.getName());
        race.setCountry(raceDtoRequest.getCountry());
        race.setDate(raceDtoRequest.getDate());

        Race updatedRace = raceRepository.save(race);
        return mapToDTO(updatedRace);
    }

    public void deleteRaceById(String id) {
        Race race = raceRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Id not found"));
        raceRepository.delete(race);
    }

}
