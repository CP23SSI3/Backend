package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @ToString
public class CreateOpenPositionDTO {
    @NotNull(message = "Open position description is required.")
    @Size(max = 200, message = "Open position's description is too long, 200 characters maximum.")
    String openPositionDesc;
    String openPositionId = UUID.randomUUID().toString();
    Integer openPositionNum;
    @NotNull(message = "Open position title is required.")
    @Size(max = 50, message = "Open position's title is too long, 50 characters maximum.")
    String openPositionTitle;
    Integer workMonth;
    BigDecimal salary;
}
