package com.example.internhub.dtos;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class EditSkillDTO {
    @NotNull(message = "Skill name is required.")
    @Size(max = 100, message = "Skill name's length is too long, 100 characters maximum.")
    String skillName;
    @NotNull(message = "Skill description is required.")
    @Size(max = 1500, message = "Skill description's length is too long, 1500 characters maximum.")
    String skillDesc;
}
