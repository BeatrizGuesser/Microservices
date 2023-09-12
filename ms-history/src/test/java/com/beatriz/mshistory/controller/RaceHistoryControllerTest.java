package com.beatriz.mshistory.controller;

import com.beatriz.mshistory.entity.Race;
import com.beatriz.mshistory.entity.RaceHistory;
import com.beatriz.mshistory.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class RaceHistoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoryService historyService;

    @Test
    void getRaceById() throws Exception {
        RaceHistory expectedRaceHistory = new RaceHistory("1", new Race("1", "Brazilian Grand Prix", "Brazil", new Date(), null, "Completed"), new Date());

        when(historyService.getRaceById("1")).thenReturn(expectedRaceHistory);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/races/history/get/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.raceResult.name").value("Brazilian Grand Prix"));
    }

    @Test
    void findAll() throws Exception {
        RaceHistory raceHistory1 = new RaceHistory("1", new Race("1", "Brazilian Grand Prix", "Brazil", new Date(), null, "Completed"), new Date());
        RaceHistory raceHistory2 = new RaceHistory("2", new Race("2", "Australian Grand Prix", "Australia", new Date(), null, "Completed"), new Date());
        List<RaceHistory> expectedRaceHistories = Arrays.asList(raceHistory1, raceHistory2);

        when(historyService.findAll()).thenReturn(expectedRaceHistories);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/races/history/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].raceResult.name").value("Brazilian Grand Prix"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].raceResult.name").value("Australian Grand Prix"));
    }
}