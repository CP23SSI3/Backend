package com.example.internhub.dtos;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
public class CreateExperienceDTO {
    @NotNull(message = "Company name is required.")
    @Size(max = 100, message = "Company name's length is too long, 100 character maximum.")
    String compName;
    int endedYear;
    String experienceId = UUID.randomUUID().toString();
    @NotNull(message = "Experience description is required.")
    @Size(max = 1500, message = "Experience description's length is too long, 1500 characters maximum.")
    String experienceDesc;
    @NotNull(message = "Experience name is required.")
    @Size(max = 100, message = "Experience name's length is too long, 100 characters maximum.")
    String experienceName;
    @NotNull(message = "Position's name is required.")
    @Size(max = 100, message = "Position name's length is too long, 100 characters maximum.")
    String position;
    @NotNull(message = "Started year is required.")
    int startedYear;
    @Valid
    @NotNull(message = "User id must be provide.")
    UserIdDTO user;

}
