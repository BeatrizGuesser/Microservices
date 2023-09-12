package com.beatriz.msraces.service;

import com.beatriz.msraces.entity.Race;
import com.beatriz.msraces.repository.RaceRepository;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
class RaceServiceTest {

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private RaceService raceService;

    @MockBean
    private RabbitMQService rabbitMQService;
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
    void deleteRaceById() {
        Race simulatedRace = new Race("1", "Race Name", "Country", new Date(), new ArrayList<>(), "RACE STARTED!");
        raceRepository.save(simulatedRace);

        raceService.deleteRaceById("1");

        assertFalse(raceRepository.existsById("1"));
    }

    @Test
    void getRandomCarsForRace() {
    }

    @Test
    void overtakeCar() {
    }

    @Test
    void finishRace() {
        Race simulatedRace = new Race("1", "Race Name", "Country", new Date(), new ArrayList<>(), "RACE FINISHED!");
        raceRepository.save(simulatedRace);

        Race finishedRace = raceService.finishRace("1");

        assertEquals("RACE FINISHED!", finishedRace.getStatus());

        ArgumentCaptor<String> raceJsonCaptor = ArgumentCaptor.forClass(String.class);
        verify(rabbitMQService).sendResultRace(raceJsonCaptor.capture());

        String raceJson = raceJsonCaptor.getValue();
        assertNotNull(raceJson);
    }
}