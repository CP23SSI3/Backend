package com.example.internhub.entities;

import com.example.internhub.responses.ResponseData;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "positiontags")
@Getter @Setter
public class PositionTag extends ResponseData {
    @Id
    @Column(name = "positionTagId", nullable = false, length = 36)
    private String positionId;

    @Column(name = "positionName", nullable = false, length = 50)
    private String positionName;
}