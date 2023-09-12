package com.beatriz.mshistory.controller;

import com.beatriz.mshistory.entity.RaceHistory;
import com.beatriz.mshistory.repository.HistoryRepository;
import com.beatriz.mshistory.service.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/races/history")
public class RaceHistoryController {
    private HistoryRepository historyRepository;
    private HistoryService historyService;

    public RaceHistoryController(HistoryRepository historyRepository, HistoryService historyService) {
        this.historyRepository = historyRepository;
        this.historyService = historyService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RaceHistory> getRaceById(@PathVariable String id){
        return ResponseEntity.ok(historyService.getRaceById(id));
    }

    @GetMapping("/get")
    public ResponseEntity<List<RaceHistory>> findAll() {
        return ResponseEntity.ok(historyService.findAll());
    }

}
