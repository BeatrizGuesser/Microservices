package com.beatriz.mshistory.service;

import com.beatriz.mshistory.entity.Race;
import com.beatriz.mshistory.entity.RaceHistory;
import com.beatriz.mshistory.exception.IdNotFoundException;
import com.beatriz.mshistory.repository.HistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void saveRaceResult(Race raceResult) {
        historyRepository.save(new RaceHistory(raceResult));
    }

    public RaceHistory getRaceById(String id){
        RaceHistory race = historyRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Id not found"));
        return race;
    }

    public List<RaceHistory> findAll() {
        List<RaceHistory> races = historyRepository.findAll();
        return races;
    }

}
