package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter @Setter
public class CreateLanguageDTO {
    String languageId = UUID.randomUUID().toString();
    @NotNull(message = "Language name is required.")
    String languageName;
    @NotNull(message = "User's id must be given.")
    UUID userId;

    public String getUserId() {
        return  userId.toString();
    }
}
