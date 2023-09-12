package com.beatriz.mshistory.repository;

import com.beatriz.mshistory.entity.RaceHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends MongoRepository<RaceHistory, String> {
}
