package com.example.internhub.services;

import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.dtos.EditPostDTO;
import com.example.internhub.entities.Post;
import com.example.internhub.exception.PostNotFoundException;
import com.example.internhub.responses.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public interface PostService {
    public List<Post> getAllPost();
    public ResponseEntity getAllPostPagination(int pageNumber, int pageSize, String searchText, String city, String district,
                                               String[] status, String[] tags, Integer month, Integer salary, HttpServletResponse res);
    public ResponseEntity getPostById(String postId, HttpServletResponse res);

    public Post getPostByPostId(String postId) throws PostNotFoundException;
    public ResponseEntity createPost(CreatePostDTO createPostDTO, HttpServletResponse res);
    public ResponseEntity editPost(String postId, EditPostDTO editPostDTO, HttpServletRequest req, HttpServletResponse res) throws IllegalAccessException;
    public ResponseEntity deletePost(String postId, HttpServletRequest req, HttpServletResponse res);
    public void postCheck();
}
