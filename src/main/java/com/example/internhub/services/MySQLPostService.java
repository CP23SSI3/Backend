package com.example.internhub.services;

import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.dtos.PostPagination;
import com.example.internhub.entities.*;
import com.example.internhub.repositories.PostRepository;
import com.example.internhub.responses.ResponseObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Primary
public class MySQLPostService implements PostService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PositionTagService positionTagService;

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
    public ResponseObject createPost(CreatePostDTO createPostDTO, HttpServletResponse res) {
        try {
            Post post = modelMapper.map(createPostDTO, Post.class);
            if (post.getOpenPositionList().size() == 0) {
                res.setStatus(400);
                return new ResponseObject(400, "At least one position is required.", null);
            }
            LocalDateTime now = LocalDateTime.now();
            post.setCreatedDate(now);
            post.setLastUpdateDate(now);
            post.setStatus(PostStatus.OPENED);
            Company company = companyService.getCompanyByCompanyId(post.getComp().getCompId());
            post.setComp(companyService.getCompany(company));
            List<OpenPosition> openPositionList = post.getOpenPositionList();
            post.setOpenPositionList(new ArrayList<>());
            for (OpenPosition openPosition : openPositionList) {
                PositionTag positionTag;
                try {
                    positionTag = positionTagService.getPositionTagByPositionTagId(openPosition.getPositionTag().getPositionTagId());
                } catch (Exception e) {
                    PositionTag position = positionTagService.getPositionTagByPositionTagName(openPosition.getOpenPositionTitle());
                    positionTag = (position == null) ? new PositionTag(UUID.randomUUID().toString(), openPosition.getOpenPositionTitle()) : position;
                }
                openPosition.setPositionTag(positionTagService.getPositionTag(positionTag));
                post.addOpenPosition(openPosition);
            }
            postRepository.save(post);
            return new ResponseObject(200, "Create post successfully.", post);
        } catch (Exception e) {
            res.setStatus(400);
            return new ResponseObject(400, e.getMessage(), null);
        }
    }

}
