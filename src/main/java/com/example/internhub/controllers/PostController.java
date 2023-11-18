package com.example.internhub.controllers;

import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.entities.Post;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin(origins = "${cors.allow.origin}")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    public ResponseObject getAllPosts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int pageSize) {
        return postService.getAllPostPagination(page, pageSize);
    }

    @GetMapping("/{postId}")
    public ResponseObject getPostById(@PathVariable String postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("")
    public ResponseObject createPost(@RequestBody CreatePostDTO post, HttpServletResponse res){
        return postService.createPost(post, res);
    }

    @DeleteMapping("/{postId}")
    public ResponseObject deletePost(@PathVariable String postId, HttpServletRequest req, HttpServletResponse res) {
        return postService.deletePost(postId, req, res);
    }
}
