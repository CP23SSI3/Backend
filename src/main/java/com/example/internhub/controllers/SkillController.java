package com.example.internhub.controllers;

import com.example.internhub.dtos.CreateSkillDTO;
import com.example.internhub.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/skills")
@CrossOrigin(origins = "${cors.allow.origin}")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("")
    public ResponseEntity addSkill(@RequestBody @Valid CreateSkillDTO createSkillDTO,
                                   HttpServletRequest req) {
        return skillService.addSkill(createSkillDTO, req);
    }

}
