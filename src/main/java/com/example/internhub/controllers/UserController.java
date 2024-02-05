package com.example.internhub.controllers;

import com.example.internhub.dtos.CreateUserDTO;
import com.example.internhub.responses.ResponseObject;
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

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    };

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId){
        return userService.deleteUser(userId);
    }

}
