package com.example.internhub.services;

import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.entities.Post;
import com.example.internhub.responses.ResponseObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public interface PostService {
    public List<Post> getAllPost();
    public ResponseObject getAllPostPagination(int pageNumber, int pageSize);
    public ResponseObject getPostById(String postId);
    public ResponseObject createPost(CreatePostDTO createPostDTO, HttpServletResponse res);
}
