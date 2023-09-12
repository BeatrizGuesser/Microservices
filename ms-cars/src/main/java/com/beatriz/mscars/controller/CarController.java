package com.beatriz.mscars.controller;

import com.beatriz.mscars.dto.CarDtoRequest;
import com.beatriz.mscars.dto.CarDtoResponse;
import com.beatriz.mscars.entity.Car;
import com.beatriz.mscars.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
    private CarService carService;
    private ModelMapper mapper;

    public CarController(CarService carService, ModelMapper mapper) {
        this.carService = carService;
        this.mapper = mapper;
    }

    @PostMapping("/post")
    public ResponseEntity<CarDtoResponse> createCar(@RequestBody CarDtoRequest carDtoRequest){
        return new ResponseEntity<>(carService.createCar(carDtoRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CarDtoResponse> getCarById(@PathVariable String id){
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Car>> findAll() {
        return ResponseEntity.ok(carService.findAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CarDtoResponse> updateCar(@RequestBody CarDtoRequest carDtoRequest, @PathVariable String id){
        CarDtoResponse carResponse = carService.updateCar(carDtoRequest, id);
        return new ResponseEntity<>(carResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable String id){
        carService.deleteCarById(id);
        return new ResponseEntity<>("Car deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/get/top10")
    public ResponseEntity<List<CarDtoResponse>> getTop10Cars() {
        return ResponseEntity.ok(carService.getTop10Cars());
    }
}
