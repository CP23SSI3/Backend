package com.example.internhub.dtos;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UserIdDTO {
    @NotNull(message = "User id must be provide.")
    String userId;
}
