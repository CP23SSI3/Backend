package com.example.internhub.services;

import com.example.internhub.entities.Post;
import com.example.internhub.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MySQLPostService implements PostService{

    @Autowired
    private PostRepository postRepository;


    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }
}
