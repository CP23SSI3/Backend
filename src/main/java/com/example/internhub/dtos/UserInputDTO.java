package com.example.internhub.dtos;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
public class UserInputDTO {

    @Size(max = 20, min = 10)
    private String name;


    @Max(40)
    @Min(10)
    private int age;
}
