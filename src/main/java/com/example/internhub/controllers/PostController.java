package com.example.internhub.controllers;

import com.example.internhub.entities.Post;
import com.example.internhub.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    public List<Post> getAllPosts(){
        return postService.getAllPost();
    }
}
