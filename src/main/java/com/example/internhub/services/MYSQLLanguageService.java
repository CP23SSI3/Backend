package com.example.internhub.services;

import com.example.internhub.repositories.LanguageRepositories;
import com.example.internhub.responses.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MYSQLLanguageService implements LanguageService {
    @Autowired
    private LanguageRepositories languageRepositories;

    @Override
    public ResponseEntity getAllLanguages() {
        return new ResponseEntity(new ResponseObject(200, "Language list i8s successfully sended.",languageRepositories.findAll()),
                null, HttpStatus.OK);
    }
}
