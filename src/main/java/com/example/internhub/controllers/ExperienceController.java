package com.example.internhub.controllers;

import com.example.internhub.dtos.CreateExperienceDTO;
import com.example.internhub.dtos.EditExperienceDTO;
import com.example.internhub.services.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/experiences")
@CrossOrigin(origins = "${cors.allow.origin}")
public class ExperienceController {
    @Autowired
    ExperienceService experienceService;

    @PostMapping("")
    public ResponseEntity addExperience(@RequestBody @Valid CreateExperienceDTO createExperienceDTO,
                                        HttpServletRequest req) {
        return experienceService.addExperience(createExperienceDTO, req);
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity deleteExperience(@PathVariable String experienceId, HttpServletRequest req) {
        return experienceService.deleteExperience(experienceId, req);
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity editExperience(@RequestBody @Valid EditExperienceDTO editExperienceDTO,
                                         @PathVariable String experienceId,
                                         HttpServletRequest req) {
        return experienceService.editExperience(editExperienceDTO, experienceId, req);
    }
}
