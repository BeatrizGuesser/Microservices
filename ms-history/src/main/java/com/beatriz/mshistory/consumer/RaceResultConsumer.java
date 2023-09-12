package com.beatriz.mshistory.consumer;

import com.beatriz.mshistory.entity.Race;
import com.beatriz.mshistory.service.HistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RaceResultConsumer {
    private final HistoryService historyService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "result")
    public void receiveRaceResult(String raceResult) throws JsonProcessingException {
        Race race = objectMapper.readValue(raceResult, Race.class);
        historyService.saveRaceResult(race);
    }
}
