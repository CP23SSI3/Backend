package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @ToString
public class CreateOpenPositionDTO {
    String openPositionId = UUID.randomUUID().toString();
    Integer openPositionNum;
    String openPositionTitle;
    String openPositionDesc;
    Integer workMonth;
    BigDecimal salary;
    CreatePositionTagDTO positionTag;
}
