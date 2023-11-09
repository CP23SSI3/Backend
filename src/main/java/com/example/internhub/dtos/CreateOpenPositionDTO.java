package com.example.internhub.dtos;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.repositories.PositionTagRepository;
import com.example.internhub.services.PositionTagService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
public class CreateOpenPositionDTO {
//    @Autowired
//    private PositionTagService positionTagService;
//    @Autowired
//            private PositionTagRepository positionTagRepository;

    String openPositionId = UUID.randomUUID().toString();
    Integer openPositionNum;
    String openPositionTitle;
    String openPositionDesc;
    Integer workMonth;
    BigDecimal salary;
    CreatePositionTag positionTag;

//    @JsonIgnore
//    PositionTag positionTag = positionTagRepository.getById(positionTagId);

//    public PositionTag getPositionTag() {
//        return positionTagService.getPositionTagById(positionTagId);
//    }
}
