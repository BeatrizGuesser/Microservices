package com.beatriz.mscars.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.beatriz.mscars.dto.CarDtoRequest;
import com.beatriz.mscars.dto.CarDtoResponse;
import com.beatriz.mscars.entity.Car;
import com.beatriz.mscars.entity.Pilot;
import com.beatriz.mscars.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarService carService;

    @Test
    void createCar() throws Exception {
        CarDtoRequest carDtoRequest = new CarDtoRequest("Toyota", "Camry", new Pilot("John", 30), new Date());

        CarDtoResponse expectedCarDtoResponse = new CarDtoResponse("1", "Toyota", "Camry", new Pilot("John", 30), new Date());

        when(carService.createCar(any(CarDtoRequest.class))).thenReturn(expectedCarDtoResponse);

        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/cars/post")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(carDtoRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        String responseContent = response.getContentAsString();
        assertThat(responseContent).isEqualTo(asJsonString(expectedCarDtoResponse));
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getCarById() throws Exception {
        String carId = "1";

        CarDtoResponse expectedCarDtoResponse = new CarDtoResponse("1", "Toyota", "Camry", new Pilot("John", 30), new Date());

        when(carService.getCarById(carId)).thenReturn(expectedCarDtoResponse);

        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/cars/get/{id}", carId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String responseContent = response.getContentAsString();
        assertThat(responseContent).isEqualTo(asJsonString(expectedCarDtoResponse));
    }

    @Test
    void findAll() throws Exception {
        List<Car> expectedCars = Arrays.asList(
                new Car("1", "Toyota", "Camry", new Pilot("John", 30), new Date()),
                new Car("2", "Honda", "Civic", new Pilot("Jane", 28), new Date())
        );

        when(carService.findAll()).thenReturn(expectedCars);

        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/cars/get")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String responseContent = response.getContentAsString();
        assertThat(responseContent).isEqualTo(asJsonString(expectedCars));
    }

    @Test
    void updateCar() throws Exception {
        String carId = "1";

        CarDtoRequest carDtoRequest = new CarDtoRequest("Toyota", "Camry", new Pilot("John", 30), new Date());

        CarDtoResponse expectedCarDtoResponse = new CarDtoResponse(carId, "Toyota", "Camry", new Pilot("John", 30), new Date());

        when(carService.updateCar(any(CarDtoRequest.class), eq(carId))).thenReturn(expectedCarDtoResponse);

        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/cars/update/{id}", carId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(carDtoRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String responseContent = response.getContentAsString();
        assertThat(responseContent).isEqualTo(asJsonString(expectedCarDtoResponse));
    }

    @Test
    void deleteCar() throws Exception {
        String carId = "1";

        mockMvc.perform(delete("/api/v1/cars/delete/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(carService).deleteCarById(carId);
    }

    @Test
    void getTop10Cars() throws Exception {
        List<CarDtoResponse> expectedCars = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            expectedCars.add(new CarDtoResponse(String.valueOf(i), "Brand " + i, "Model " + i, new Pilot("Pilot " + i, 30 + i), new Date()));
        }

        when(carService.getTop10Cars()).thenReturn(expectedCars);

        mockMvc.perform(get("/api/v1/cars/get/top10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[9].id").value("10"));
    }
}