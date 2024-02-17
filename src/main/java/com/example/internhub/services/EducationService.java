package com.example.internhub.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EducationService {
    public ResponseEntity getAllEducations();
}
