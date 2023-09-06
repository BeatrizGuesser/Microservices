package com.beatriz.mscars.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Car {
    @Id
    private String id;
    private String brand;
    private String model;
    private Pilot pilot;
    @JsonFormat(pattern = "yyyy")
    private Date year;
}
