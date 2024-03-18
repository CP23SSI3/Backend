package com.example.internhub.services;

import com.example.internhub.dtos.CreateLanguageDTO;
import com.example.internhub.entities.Language;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface LanguageService {
    public ResponseEntity addLanguage(CreateLanguageDTO createLanguageDTO, HttpServletRequest req);
    public ResponseEntity getAllLanguages();
}
