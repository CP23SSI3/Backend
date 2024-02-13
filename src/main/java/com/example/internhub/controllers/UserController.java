package com.example.internhub.controllers;

import com.example.internhub.dtos.CreateUserDTO;
import com.example.internhub.dtos.EditUserDTO;
import com.example.internhub.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "${cors.allow.origin}")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/username-email-checking")
    public ResponseEntity checkIfUsernameAndEmailExisted(@RequestParam String username, @RequestParam String email){
        return userService.checkIfUsernameAndEmailExisted(username, email);
    }

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    };

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId){
        return userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity editUserGeneralInformation(@PathVariable String userId,
                                                     @RequestBody EditUserDTO editUserDTO) {
        return userService.editUserGeneralInformation(userId, editUserDTO);
    }

    @GetMapping("")
    public ResponseEntity getAllUsersPagination(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int pageSize,
                                                HttpServletResponse res){
        return userService.getAllUserPagination(page, pageSize, res);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@PathVariable String userId,
                                      HttpServletRequest req,
                                      HttpServletResponse res) {
        return userService.getResponseUserById(userId, req, res);
    }

}
