package com.beatriz.mscars.repository;

import com.beatriz.mscars.entity.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, String> {
}
