package com.beatriz.mscars.dto;

import com.beatriz.mscars.entity.Pilot;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDtoRequest {
    private String brand;
    private String model;
    private Pilot pilot;
    @JsonFormat(pattern = "yyyy")
    private Date year;
}
