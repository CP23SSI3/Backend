package com.example.internhub.controllers;

import com.example.internhub.dtos.CreateLanguageDTO;
import com.example.internhub.dtos.EditLanguageDTO;
import com.example.internhub.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/languages")
@CrossOrigin(origins = "${cors.allow.origin}")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @PostMapping("")
    public ResponseEntity addLanguage(@RequestBody CreateLanguageDTO createLanguageDTO, HttpServletRequest req) {
        return languageService.addLanguage(createLanguageDTO, req);
    }

    @DeleteMapping("/{languageId}")
    public ResponseEntity deleteLanguage(@PathVariable String languageId, HttpServletRequest req) {
        return languageService.deleteLanguage(languageId, req);
    }

    @PutMapping("/{languageId}")
    public ResponseEntity editLanguage(@RequestBody EditLanguageDTO editLanguageDTO,
                                       @PathVariable String languageId,
                                       HttpServletRequest req) {
        return languageService.editLanguage(editLanguageDTO,languageId, req);
    }

    @GetMapping("")
    public ResponseEntity getAllLanguages(){
        return languageService.getAllLanguages();
    }

}
