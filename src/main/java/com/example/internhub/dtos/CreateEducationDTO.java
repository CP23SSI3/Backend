package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.Year;
import java.util.UUID;

@Getter
public class CreateEducationDTO {
    @NotNull(message = "Degree's length is required.")
    @Size(max = 50, message = "Degree's length is too long, 50 characters maximum.")
    String degree;
    @Size(max = 1500, message = "Education description's length is too long, 1500 characters maximum.")
    String educationDesc;
    String educationId = UUID.randomUUID().toString();
    @Size(max = 100, message = "Field's length is too long, 5 characters maximum.")
    String field;
    @Size(max = 5, message = "Grade's length is too long, 5 characters maximum.")
    String grade;
    int graduatedYear;
    @NotNull(message = "School name is required.")
    @Size(max = 100, message = "School name's length is too long, 100 characters maximum.")
    String schoolName;
    @NotNull(message = "Started year is required.")
    int startedYear;
    UserIdDTO user;
}
