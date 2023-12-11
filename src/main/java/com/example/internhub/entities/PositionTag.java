package com.example.internhub.entities;

import lombok.*;
import com.example.internhub.responses.Object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "positionTags")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class PositionTag extends Object {
    @Id
    @Column(name = "positionTagName", nullable = false, length = 50)
    private String positionTagName;

}