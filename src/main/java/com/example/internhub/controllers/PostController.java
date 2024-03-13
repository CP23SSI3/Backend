package com.example.internhub.controllers;

import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.dtos.EditPostDTO;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin(origins = "${cors.allow.origin}")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("")
    public ResponseEntity createPost(@Valid @RequestBody CreatePostDTO post,
                                     HttpServletRequest req,
                                     HttpServletResponse res){
        return postService.createPost(post, req, res);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable String postId, HttpServletRequest req, HttpServletResponse res) {
        return postService.deletePost(postId, req, res);
    }

    @PutMapping("/{postId}")
    public ResponseEntity editPost(@PathVariable String postId, @RequestBody @Valid EditPostDTO editPostDTO
            , HttpServletRequest req, HttpServletResponse res) throws IllegalAccessException {
        return postService.editPost(postId, editPostDTO, req, res);
    }

    @GetMapping("")
    public ResponseEntity getAllPosts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int pageSize,
                                      @RequestParam(defaultValue = "") String q,
                                      @RequestParam(defaultValue = "") String city,
                                      @RequestParam(defaultValue = "") String district,
                                      @RequestParam(required = false) String[] status,
                                      @RequestParam(required = false) String[] tags,
                                      @RequestParam(defaultValue = "0") Integer month,
                                      @RequestParam(defaultValue = "0") Integer salary,
                                      HttpServletResponse res) {
        return postService.getAllPostPagination(page, pageSize, q, city, district, status, tags, month, salary, res);
    }

    @GetMapping("/{postId}")
    public ResponseEntity getPostById(@PathVariable String postId, HttpServletResponse res) {
        return postService.getPostById(postId, res);
    }
}
