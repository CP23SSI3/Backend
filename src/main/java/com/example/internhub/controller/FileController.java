package com.example.internhub.controller;

import com.example.internhub.dtos.UserInputDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.*;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/test")
@Validated
public class FileController {

    @GetMapping
    public String test() {
        return "get mapping";
    }

    @GetMapping("/data")
    public String data(@RequestBody UserInputDTO user) {
        return user.getName();
    }

    @PostMapping("/file")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return file.getOriginalFilename();
    }

    @PostMapping("/datafile")
    public String uplaodDataFile(@RequestParam("file") MultipartFile file, @RequestParam("user")UserInputDTO user) {
        return file.getOriginalFilename() + user.getName();
    }

    @PostMapping("/header")
    public String upHeader(@RequestHeader("file") MultipartFile file, @Valid @RequestBody UserInputDTO user) {
        return file.getOriginalFilename() + user.getName();
    }

    @PostMapping("/mapper")
    public String testObjectMapper(@RequestParam("file") MultipartFile file, @RequestParam("user") String userJson){
        Gson gson = new Gson();

//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
        UserInputDTO user = gson.fromJson(userJson, UserInputDTO.class);
//        Set<ConstraintViolation<UserInputDTO>> violations = validator.validate(user);
//        System.out.println(violations);
        return file.getOriginalFilename() + user.getName();
    }
}
