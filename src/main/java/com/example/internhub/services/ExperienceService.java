package com.example.internhub.services;

import com.example.internhub.dtos.CreateExperienceDTO;
import com.example.internhub.dtos.EditExperienceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface ExperienceService {
    public ResponseEntity addExperience(CreateExperienceDTO createExperienceDTO, HttpServletRequest req);
    public ResponseEntity deleteExperience(String experienceId, HttpServletRequest req);
    public ResponseEntity editExperience(EditExperienceDTO editExperienceDTO, String experienceId, HttpServletRequest req);
}
