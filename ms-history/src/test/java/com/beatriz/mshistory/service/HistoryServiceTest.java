package com.beatriz.mshistory.service;

import com.beatriz.mshistory.entity.Race;
import com.beatriz.mshistory.entity.RaceHistory;
import com.beatriz.mshistory.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HistoryServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HistoryService historyService;

    @BeforeEach
    public void setUp() {
        Race race1 = new Race("1", "Brazilian Grand Prix", "Brazil", new Date(), null, "Completed");
        RaceHistory raceHistory1 = new RaceHistory("1", race1, new Date());

        Race race2 = new Race("2", "Australian Grand Prix", "Australia", new Date(), null, "Completed");
        RaceHistory raceHistory2 = new RaceHistory("2", race2, new Date());

        when(historyService.findAll()).thenReturn(Arrays.asList(raceHistory1, raceHistory2));

        when(historyService.getRaceById("1")).thenReturn(raceHistory1);
        when(historyService.getRaceById("2")).thenReturn(raceHistory2);
    }

    @Test
    void getRaceById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/races/history/get/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.raceResult.name").value("Brazilian Grand Prix"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/races/history/get/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.raceResult.name").value("Australian Grand Prix"));

        verify(historyService, times(1)).getRaceById("1");
        verify(historyService, times(1)).getRaceById("2");
    }

    @Test
    void findAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/races/history/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].raceResult.name").value("Brazilian Grand Prix"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].raceResult.name").value("Australian Grand Prix"))
                .andReturn();

        verify(historyService, times(1)).findAll();
    }

    @Test
    void saveRaceResult() {
    }
}