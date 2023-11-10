package com.example.internhub.services;

import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.dtos.PostPagination;
import com.example.internhub.entities.Address;
import com.example.internhub.entities.Company;
import com.example.internhub.entities.Post;
import com.example.internhub.entities.PostStatus;
import com.example.internhub.repositories.PostRepository;
import com.example.internhub.responses.ResponseObject;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Primary
public class MySQLPostService implements PostService{

    private String compId = "8e20782f-2807-4f13-a11e-0fb9ff955488";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AddressService addressService;

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public ResponseObject getAllPostPagination(int pageNumber, int pageSize) {
        Page<Post> postList = postRepository.findAll(PageRequest.of(pageNumber,pageSize));
        PostPagination postPagination = modelMapper.map(postList, PostPagination.class);
        return new ResponseObject(200, "The post's list is succesfully sended.", postPagination);
    }

    @Override
    public ResponseObject getPostById(String postId) {
        try {
            Post post = postRepository.findById(postId).orElseThrow();
            return new ResponseObject(200, "The post is successfully sended.", post);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found");
        }
    }

    @Override
    public Post createPost(CreatePostDTO createPostDTO) {
        Post post = modelMapper.map(createPostDTO, Post.class);
        Company company = companyService.getCompanyById(post.getComp().getCompId());
        System.out.println(company);
//        post.setComp(company);
        return post;
    }

}
