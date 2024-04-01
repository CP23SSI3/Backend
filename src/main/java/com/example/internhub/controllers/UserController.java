package com.example.internhub.controllers;

import com.example.internhub.dtos.CreateUserDTO;
import com.example.internhub.dtos.EditUserDTO;
import com.example.internhub.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "${cors.allow.origin}")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/username-checking")
    public ResponseEntity checkIfUsernameExisted(@RequestParam String username) {
        return userService.checkIfUsernameExisted(username);
    }

    @GetMapping("/username-email-checking")
    public ResponseEntity checkIfUsernameAndEmailExisted(@RequestParam String username, @RequestParam String email){
        return userService.checkIfUsernameAndEmailExisted(username, email);
    }

    @PostMapping("")
    public ResponseEntity createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    };

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId, HttpServletRequest req){
        return userService.deleteUser(req, userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity editUserGeneralInformation(@PathVariable String userId,
                                                     @Valid @RequestBody EditUserDTO editUserDTO,
                                                     HttpServletRequest req) {
        return userService.editUserGeneralInformation(req, userId, editUserDTO);
    }

    @GetMapping("")
    public ResponseEntity getAllUsersPagination(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int pageSize,
                                                @RequestParam(defaultValue = "") String q,
                                                HttpServletResponse res){
        return userService.getAllUserPagination(page, pageSize, q, res);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@PathVariable String userId,
                                      HttpServletRequest req,
                                      HttpServletResponse res) {
        return userService.getResponseUserById(userId, req, res);
    }

}
