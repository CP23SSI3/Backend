package com.example.internhub.entities;

import lombok.*;
import com.example.internhub.responses.ResponseData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "positiontags")
@Getter @Setter @ToString
@NoArgsConstructor
public class PositionTag extends ResponseData {
    @Id
//    @Column(name = "positionTagId", nullable = false, length = 36)
//    private String positionTagId;

    @Column(name = "positionTagName", nullable = false, length = 50)
    private String positionTagName;

    public PositionTag(String positionName){
        new PositionTag(positionName);
    }
}