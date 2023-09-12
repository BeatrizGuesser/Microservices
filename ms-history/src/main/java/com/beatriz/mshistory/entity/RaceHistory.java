package com.beatriz.mshistory.entity;

import com.beatriz.mshistory.dto.CarDtoResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class RaceHistory {
    @Id
    private String id;
    private Race raceResult;
    @CreatedDate
    private Date insertedAt;

    public RaceHistory(Race raceResult) {
        this.raceResult = raceResult;
    }

}
