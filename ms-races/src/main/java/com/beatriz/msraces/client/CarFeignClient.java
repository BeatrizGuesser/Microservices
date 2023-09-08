package com.beatriz.msraces.client;

import com.beatriz.msraces.dto.CarDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-cars", url = "http://localhost:8080")
public interface CarFeignClient {
    @GetMapping("/api/v1/cars/get/top10")
    List<CarDtoResponse> getTop10Cars();
}
