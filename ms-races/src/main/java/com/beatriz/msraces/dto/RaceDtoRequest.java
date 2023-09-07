package com.beatriz.msraces.dto;

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
public class RaceDtoRequest {
    private String id;
    private String name;
    private String country;
    @JsonFormat(pattern = "yyyy")
    private Date date;
}
