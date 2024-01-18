package com.example.internhub.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "positionTags")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class PositionTag {
    @Id
    @Column(name = "positionTagName", nullable = false, length = 50)
    private String positionTagName;

}