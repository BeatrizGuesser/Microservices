package com.beatriz.msraces.controller;

import com.beatriz.msraces.client.CarFeignClient;
import com.beatriz.msraces.entity.Race;
import com.beatriz.msraces.service.RaceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class RaceControllerTest {
    private RaceController raceController;
    @Mock
    private RaceService raceService;
    private CarFeignClient carFeignClient;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createRace() {
    }

    @Test
    void getRaceById() {
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/races/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].country").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cars").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").isNotEmpty());
    }

    @Test
    void updateRace() {
    }

    @Test
    void deleteRace() {
        raceController = new RaceController(raceService, carFeignClient);
        String validRaceId = "1";

        doNothing().when(raceService).deleteRaceById(validRaceId);

        ResponseEntity<String> response = raceController.deleteRace(validRaceId);

        verify(raceService, times(1)).deleteRaceById(validRaceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Race deleted successfully.", response.getBody());
    }

    @Test
    void getTop10Cars() {
    }

    @Test
    void overtakeCar() {
    }

    @Test
    void finishRace() {
        raceController = new RaceController(raceService, carFeignClient);
        String validRaceId = "1";

        Race simulatedFinishedRace = new Race("1", "Race Name", "Country", new Date(), new ArrayList<>(), "RACE FINISHED!");
        when(raceService.finishRace(validRaceId)).thenReturn(simulatedFinishedRace);

        ResponseEntity<Race> response = raceController.finishRace(validRaceId);

        verify(raceService, times(1)).finishRace(validRaceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("RACE FINISHED!", response.getBody().getStatus());
    }
}