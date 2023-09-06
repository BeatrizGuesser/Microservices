package com.beatriz.mscars.repository;

import com.beatriz.mscars.entity.Pilot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PilotRepository extends MongoRepository<Pilot, Long> {
}
