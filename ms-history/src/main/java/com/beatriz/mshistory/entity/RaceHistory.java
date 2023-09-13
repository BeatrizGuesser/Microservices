package com.beatriz.mshistory.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
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
