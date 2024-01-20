package com.example.internhub.controllers;

import com.example.internhub.dtos.UserLoginDTO;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "${cors.allow.origin}")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity logIn(@Valid @RequestBody UserLoginDTO userLoginDTO,
                                HttpServletRequest req,
                                HttpServletResponse res) {
        return authService.logIn(userLoginDTO);
    }

}
