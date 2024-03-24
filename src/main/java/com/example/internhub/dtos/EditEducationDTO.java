package com.example.internhub.dtos;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
public class EditEducationDTO {
    @NotNull(message = "Degree's length is required.")
    @Size(max = 50, message = "Degree's length is too long, 50 characters maximum.")
    String degree;
    @Size(max = 1500, message = "Education description's length is too long, 1500 characters maximum.")
    String educationDesc;
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
}
