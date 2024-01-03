package com.example.internhub.controllers;

import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.dtos.EditPostDTO;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin(origins = "${cors.allow.origin}")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    public ResponseObject getAllPosts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int pageSize,
                                      @RequestParam(defaultValue = "") String q,
                                      @RequestParam(defaultValue = "") String city,
                                      @RequestParam(defaultValue = "") String district,
                                      @RequestParam(defaultValue = "OPENED")String status,
                                      HttpServletResponse res) {
        return postService.getAllPostPagination(page, pageSize, q, city, district, status, res);
    }

    @GetMapping("/{postId}")
    public ResponseObject getPostById(@PathVariable String postId, HttpServletResponse res) {
        return postService.getPostById(postId, res);
    }

    @PostMapping("")
    public ResponseObject createPost(@Valid @RequestBody CreatePostDTO post, HttpServletResponse res){
        return postService.createPost(post, res);
    }

    @PutMapping("/{postId}")
    public ResponseObject editPost(@PathVariable String postId, @RequestBody @Valid EditPostDTO editPostDTO
            , HttpServletRequest req, HttpServletResponse res) throws IllegalAccessException {
        return postService.editPost(postId, editPostDTO, req, res);
    }

    @DeleteMapping("/{postId}")
    public ResponseObject deletePost(@PathVariable String postId, HttpServletRequest req, HttpServletResponse res) {
        return postService.deletePost(postId, req, res);
    }
}
