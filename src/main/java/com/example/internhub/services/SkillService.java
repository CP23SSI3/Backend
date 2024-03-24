package com.example.internhub.services;

import com.example.internhub.dtos.CreateSkillDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface SkillService {
    public ResponseEntity addSkill(CreateSkillDTO createSkillDTO, HttpServletRequest req);
}
