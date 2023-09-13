package com.beatriz.mshistory.entity;

import com.beatriz.mshistory.dto.CarDtoResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Race {
    private String id;
    private String name;
    private String country;
    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date date;
    private List<CarDtoResponse> cars;
    private String status;

}
