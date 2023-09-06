package com.beatriz.mscars.service;

import com.beatriz.mscars.dto.CarDtoRequest;
import com.beatriz.mscars.dto.CarDtoResponse;
import com.beatriz.mscars.entity.Car;
import com.beatriz.mscars.repository.CarRepository;
import com.beatriz.mscars.repository.PilotRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private CarRepository carRepository;
    private PilotRepository pilotRepository;
    private ModelMapper mapper;

    public CarService(CarRepository carRepository, PilotRepository pilotRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.pilotRepository = pilotRepository;
        this.mapper = mapper;
    }

    // convert Entity into DTO
    private CarDtoResponse mapToDTO(Car car){
        CarDtoResponse carDtoResponse = mapper.map(car, CarDtoResponse.class);
        return carDtoResponse;
    }

    // convert DTO to entity
    private Car mapToEntity(CarDtoRequest carDtoRequest){
        Car car = mapper.map(carDtoRequest, Car.class);
        return car;
    }

    public CarDtoResponse createCar(CarDtoRequest carDtoRequest) {

        // convert DTO to entity
        Car car = mapToEntity(carDtoRequest);
        Car newCar = carRepository.save(car);

        // convert entity to DTO
        CarDtoResponse carResponse = mapToDTO(newCar);
        return carResponse;
    }

    public CarDtoResponse getCarById(String id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return mapToDTO(car);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public CarDtoResponse updateCar(CarDtoRequest carDtoRequest, String id) {
        // get post by id from the database
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException());

        car.setBrand(carDtoRequest.getBrand());
        car.setModel(carDtoRequest.getModel());
        car.setPilot(carDtoRequest.getPilot());
        car.setYear(carDtoRequest.getYear());

        Car updatedCar = carRepository.save(car);
        return mapToDTO(updatedCar);
    }

    public void deleteCarById(String id) {
        // get post by id from the database
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException());
        carRepository.delete(car);
    }
}
