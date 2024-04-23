package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class EditLanguageDTO {
    @NotNull(message = "Language name is required.")
    @Size(max = 50, message = "Language name's length is too long, 50 characters maximum.")
    String languageName;
}
