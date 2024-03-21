package com.example.internhub.services;

import com.example.internhub.dtos.CreateLanguageDTO;
import com.example.internhub.dtos.EditLanguageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface LanguageService {
    public ResponseEntity addLanguage(CreateLanguageDTO createLanguageDTO, HttpServletRequest req);
    public ResponseEntity deleteLanguage(String languageId, HttpServletRequest req);
    public ResponseEntity editLanguage(EditLanguageDTO editLanguageDTO, String languageId, HttpServletRequest req);
    public ResponseEntity getAllLanguages();
}
