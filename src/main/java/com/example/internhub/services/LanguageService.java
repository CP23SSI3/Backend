package com.example.internhub.services;

import com.example.internhub.entities.Language;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LanguageService {
    public ResponseEntity getAllLanguages();
}
