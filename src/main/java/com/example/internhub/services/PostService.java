package com.example.internhub.services;

import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.dtos.EditPostDTO;
import com.example.internhub.entities.Post;
import com.example.internhub.responses.ResponseObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public interface PostService {
    public List<Post> getAllPost();
    public ResponseObject getAllPostPagination(int pageNumber, int pageSize, String searchText, String city, String district);
    public ResponseObject getPostById(String postId, HttpServletResponse res);

    public Post getPostByPostId(String postId);
    public ResponseObject createPost(CreatePostDTO createPostDTO, HttpServletResponse res);
    public ResponseObject editPost(String postId, EditPostDTO editPostDTO, HttpServletRequest req, HttpServletResponse res) throws IllegalAccessException;
    public ResponseObject deletePost(String postId, HttpServletRequest req, HttpServletResponse res);

}
