package com.example.internhub.services;

import com.example.internhub.dtos.PostPagination;
import com.example.internhub.entities.Post;
import com.example.internhub.repositories.PostRepository;
import com.example.internhub.responses.ResponseObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MySQLPostService implements PostService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public ResponseObject getAllPostPagination(int pageNumber, int pageSize) {
        Page<Post> postList = postRepository.findAll(PageRequest.of(pageNumber,pageSize));
        PostPagination postPagination = modelMapper.map(postList, PostPagination.class);
        return new ResponseObject(200, "The post is succesfullysended", postPagination);
    }
}
