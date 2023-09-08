package com.beatriz.msraces.dto;

import com.beatriz.msraces.entity.Pilot;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDtoResponse {
    private String id;
    private String brand;
    private String model;
    private Pilot pilot;
    @JsonFormat(pattern = "yyyy")
    private Date year;
}
