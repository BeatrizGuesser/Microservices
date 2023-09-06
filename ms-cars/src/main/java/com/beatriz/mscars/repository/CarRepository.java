package com.beatriz.mscars.repository;

import com.beatriz.mscars.entity.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface CarRepository extends MongoRepository<Car, String> {
    boolean existsByPilotNameAndPilotAge(String name, Integer age);

    boolean existsByBrandAndModelAndYear(String brand, String model, Date year);

}
