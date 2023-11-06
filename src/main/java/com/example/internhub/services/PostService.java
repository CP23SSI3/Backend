package com.example.internhub.services;

import com.example.internhub.entities.Post;
import com.example.internhub.responses.ResponseObject;

import java.util.List;

public interface PostService {
    public List<Post> getAllPost();
    public ResponseObject getAllPostPagination(int pageNumber, int pageSize);
    public ResponseObject getPostById(String postId);
}
