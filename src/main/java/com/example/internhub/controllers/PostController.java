package com.example.internhub.controllers;

import com.example.internhub.entities.Post;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

//    @GetMapping("")
//    public List<Post> getAllPosts(){
//        return postService.getAllPost();
//    }

    @GetMapping("")
    public ResponseObject getAllPosts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int pageSize) {
        return postService.getAllPostPagination(page, pageSize);
    }

    @GetMapping("/{postId}")
    public ResponseObject getPostById(@PathVariable String postId) {
        System.out.println(postId);
        return postService.getPostById(postId);
    }

//    @GetMapping("/{postId}")
//    public ResponseObject getPostById(@PathVariable("postId") String postId) {
//        return postService.getPostById(postId);
//    }

}
