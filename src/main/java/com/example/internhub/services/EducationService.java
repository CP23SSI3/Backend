package com.example.internhub.services;

import com.example.internhub.dtos.CreateEducationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface EducationService {
    public ResponseEntity addEducation(CreateEducationDTO createEducationDTO, HttpServletRequest req);
    public ResponseEntity getAllEducations();
}
