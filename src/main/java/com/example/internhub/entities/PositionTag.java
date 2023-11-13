package com.example.internhub.entities;

import lombok.*;
import com.example.internhub.responses.ResponseData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "positiontags")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class PositionTag extends ResponseData {
    @Id
    @Column(name = "positionTagId", nullable = false, length = 36)
    private String positionTagId;

    @Column(name = "positionName", nullable = false, length = 50)
    private String positionName;
}