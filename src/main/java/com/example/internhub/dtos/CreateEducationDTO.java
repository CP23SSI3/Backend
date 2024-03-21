package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter @Setter
public class CreateEducationDTO {
    @NotNull(message = "User's id must be given.")
    String userId;
    String skillsId = UUID.randomUUID().toString();
    @NotNull(message = "Skill name is required.")
    String skillName;
    String skillDesc;
}
