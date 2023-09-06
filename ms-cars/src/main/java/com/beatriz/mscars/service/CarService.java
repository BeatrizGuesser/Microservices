package com.beatriz.mscars.service;

import com.beatriz.mscars.dto.CarDtoRequest;
import com.beatriz.mscars.dto.CarDtoResponse;
import com.beatriz.mscars.entity.Car;
import com.beatriz.mscars.exception.IdNotFoundException;
import com.beatriz.mscars.exception.NotUniqueCarException;
import com.beatriz.mscars.exception.NotUniquePilotException;
import com.beatriz.mscars.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ModelMapper mapper;

    public CarService(CarRepository carRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.mapper = mapper;
    }

    private CarDtoResponse mapToDTO(Car car){
        CarDtoResponse carDtoResponse = mapper.map(car, CarDtoResponse.class);
        return carDtoResponse;
    }

    private Car mapToEntity(CarDtoRequest carDtoRequest){
        Car car = mapper.map(carDtoRequest, Car.class);
        return car;
    }

    public boolean isUniquePilot(String name, Integer age){
        return carRepository.existsByPilotNameAndPilotAge(name, age);
    }

    public boolean isUniqueCar(CarDtoRequest carDtoRequest){
        return carRepository.existsByBrandAndModelAndYear(carDtoRequest.getBrand(), carDtoRequest.getModel(), carDtoRequest.getYear());
    }

    public CarDtoResponse createCar(CarDtoRequest carDtoRequest) {
        if(isUniquePilot(carDtoRequest.getPilot().getName(), carDtoRequest.getPilot().getAge())){
            throw new NotUniquePilotException("There is already a identical pilot");
        }

        if (isUniqueCar(carDtoRequest)){
            throw new NotUniqueCarException("There is already a identical car");
        }

        Car car = mapper.map(carDtoRequest, Car.class);
        Car newCar = carRepository.save(car);

//        Car car = mapToEntity(carDtoRequest);
//        Car newCar = carRepository.save(car);
//
//        CarDtoResponse carResponse = mapToDTO(newCar);
        return mapper.map(newCar, CarDtoResponse.class);
    }

    public CarDtoResponse getCarById(String id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Id not found"));
        return mapToDTO(car);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public CarDtoResponse updateCar(CarDtoRequest carDtoRequest, String id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Id not found"));

        car.setBrand(carDtoRequest.getBrand());
        car.setModel(carDtoRequest.getModel());
        car.setPilot(carDtoRequest.getPilot());
        car.setYear(carDtoRequest.getYear());

        Car updatedCar = carRepository.save(car);
        return mapToDTO(updatedCar);
    }

    public void deleteCarById(String id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Id not found"));
        carRepository.delete(car);
    }
}
