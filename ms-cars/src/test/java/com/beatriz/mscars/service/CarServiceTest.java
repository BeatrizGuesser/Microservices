package com.beatriz.mscars.service;

import com.beatriz.mscars.dto.CarDtoRequest;
import com.beatriz.mscars.entity.Car;
import com.beatriz.mscars.entity.Pilot;
import com.beatriz.mscars.repository.CarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(carRepository);
    }

    @Test
    void createCar() throws Exception{
        CarDtoRequest carDtoRequest = new CarDtoRequest();
        carDtoRequest.setBrand("TestBrand");
        carDtoRequest.setModel("TestModel");
        carDtoRequest.setYear(new Date());

        Pilot pilot = new Pilot();
        pilot.setName("TestPilot");
        pilot.setAge(30);
        carDtoRequest.setPilot(pilot);

        Car savedCar = new Car();
        savedCar.setId("1");
        savedCar.setBrand(carDtoRequest.getBrand());
        savedCar.setModel(carDtoRequest.getModel());
        savedCar.setYear(carDtoRequest.getYear());
        savedCar.setPilot(pilot);

        Mockito.when(carRepository.save(Mockito.any())).thenReturn(savedCar);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/cars/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDtoRequest)))
                .andExpect(status().is(201));
    }

    @Test
    void getCarById() throws Exception{
        String existingCarId = "1";
        Car existingCar = new Car(existingCarId, "Toyota", "Camry", new Pilot("John", 30), new Date());

        Mockito.when(carRepository.findById(existingCarId)).thenReturn(Optional.of(existingCar));

        String carId = "1";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cars/get/" + carId))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Camry"));
    }

    @Test
    void findAll() throws Exception {
        List<Car> cars = Arrays.asList(
                new Car("1", "Toyota", "Camry", new Pilot("John", 30), new Date()),
                new Car("2", "Honda", "Civic", new Pilot("Alice", 25), new Date())
        );
        Mockito.when(carRepository.findAll()).thenReturn(cars);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cars/get"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].brand").value("Honda"));
    }

    @Test
    void updateCar() throws Exception {

        String existingCarId = "1";
        Car existingCar = new Car(existingCarId, "Toyota", "Camry", new Pilot("John", 30), new Date());

        Mockito.when(carRepository.findById(existingCarId)).thenReturn(Optional.of(existingCar));

        Mockito.when(carRepository.save(Mockito.any(Car.class))).thenAnswer(invocation -> {
            Car updatedCar = invocation.getArgument(0);
            return updatedCar;
        });

        String carId = "1";

        CarDtoRequest updatedCarDto = new CarDtoRequest("Honda", "Civic", new Pilot("Alice", 25), new Date());
        String updatedCarDtoJson = objectMapper.writeValueAsString(updatedCarDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cars/update/" + carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCarDtoJson))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Honda"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Civic"));

    }

    @Test
    void deleteCarById() throws Exception {
        String existingCarId = "1";
        Car existingCar = new Car(existingCarId, "Toyota", "Camry", new Pilot("John", 30), new Date());

        Mockito.when(carRepository.findById(existingCarId)).thenReturn(Optional.of(existingCar));

        Mockito.doNothing().when(carRepository).delete(existingCar);

        String carId = "1";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cars/delete/" + carId))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void isUniquePilot() {
    }

    @Test
    void isUniqueCar() {
    }

    @Test
    void getTop10Cars() {
    }
}