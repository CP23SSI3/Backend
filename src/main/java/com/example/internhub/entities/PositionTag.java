package com.example.internhub.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "positiontags")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PositionTag {
    @Id
    @Column(name = "positionTagId", nullable = false, length = 36)
    private String positionTagId;

    @Column(name = "positionName", nullable = false, length = 50)
    private String positionName;
}