package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter @Setter
public class CreateLanguageDTO {
    String languageId = UUID.randomUUID().toString();
    @NotNull(message = "Language name is required.")
    @Size(max = 50, message = "Language name's length is too lang, 50 characters maximum.")
    String languageName;
    @Valid
    @NotNull(message = "User id must be provide.")
    UserIdDTO user;

}
