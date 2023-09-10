package com.beatriz.msraces.controller;

import com.beatriz.msraces.client.CarFeignClient;
import com.beatriz.msraces.dto.CarDtoResponse;
import com.beatriz.msraces.dto.RaceDtoRequest;
import com.beatriz.msraces.dto.RaceDtoResponse;
import com.beatriz.msraces.entity.Race;
import com.beatriz.msraces.service.RaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/races")
public class RaceController {
    private RaceService raceService;
    private CarFeignClient carFeignClient;

    public RaceController(RaceService raceService, CarFeignClient carFeignClient) {
        this.raceService = raceService;
        this.carFeignClient = carFeignClient;
    }

    @PostMapping("/post")
    public ResponseEntity<RaceDtoResponse> createRace(@RequestBody RaceDtoRequest raceDtoRequest){
        return new ResponseEntity<>(raceService.createRace(raceDtoRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Race> getRaceById(@PathVariable String id){
        return ResponseEntity.ok(raceService.getRaceById(id));
    }

    @GetMapping("/get")
    public List<RaceDtoResponse> findAll() {
        return raceService.findAll();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RaceDtoResponse> updateRace(@RequestBody RaceDtoRequest raceDtoRequest, @PathVariable String id){
        RaceDtoResponse raceResponse = raceService.updateRace(raceDtoRequest, id);
        return new ResponseEntity<>(raceResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRace(@PathVariable String id){
        raceService.deleteRaceById(id);
        return new ResponseEntity<>("Race deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/get10")
    public List<CarDtoResponse> getTop10Cars(){
        return carFeignClient.getTop10Cars();
    }

    @PostMapping("/race/{raceId}/overtake/{carId}/{carToOvertakeId}")
    public ResponseEntity<RaceDtoResponse> overtakeCar(
            @PathVariable String raceId,
            @PathVariable String carId,
            @PathVariable String carToOvertakeId
    ) {
        RaceDtoResponse updatedRace = raceService.overtakeCar(raceId, carId, carToOvertakeId);
        return ResponseEntity.ok(updatedRace);
    }
}
