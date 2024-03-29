package com.example.internhub.services;

import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.dtos.EditPostDTO;
import com.example.internhub.dtos.PostPagination;
import com.example.internhub.entities.*;
import com.example.internhub.repositories.PostPositionTagRepository;
import com.example.internhub.repositories.PostRepository;
import com.example.internhub.responses.ResponseObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class MySQLPostService implements PostService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private PositionTagService positionTagService;
    @Autowired
    private ClassService classService;
    @Autowired
    private OpenPositionService openPositionService;
    @Autowired
    private PostPositionTagService postPositionTagService;

    @Autowired
    private PostPositionTagRepository postPositionTagRepository;

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public ResponseObject getAllPostPagination(int pageNumber, int pageSize) {
        Page<Post> postList = postRepository.findAll(PageRequest.of(pageNumber, pageSize));
        PostPagination postPagination = modelMapper.map(postList, PostPagination.class);
        return new ResponseObject(200, "The post's list is successfully sent.", postPagination);
    }

    @Override
    public ResponseObject getPostById(String postId, HttpServletResponse res) {
        try {
            Post post = getPostByPostId(postId);
            post.view();
            postRepository.save(post);
            return new ResponseObject(200, "The post is successfully sent.", post);
        } catch (Exception e) {
            res.setStatus(404);
            return new ResponseObject(404, e.getMessage(), null);
        }
    }


    @Override
    public Post getPostByPostId(String postId) {
        try {
            return postRepository.findById(postId).orElseThrow();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post id " + postId + " not found.");
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
                post.addOpenPosition(openPosition);
            }
            List<PostPositionTag> postPositionTagList = createPostDTO.getPostTagList();
            post.setPostTagList(new ArrayList<>());
            for (PostPositionTag tag : postPositionTagList) {
                post.addPostTag(tag);
            }
            postRepository.save(post);
            return new ResponseObject(200, "Create post successfully.", post);
        } catch (ResponseStatusException e) {
            int statusCode= e.getStatus().value();
            res.setStatus(statusCode);
            return new ResponseObject(statusCode, e.getMessage(), null);
        } catch (Exception e) {
            res.setStatus(400);
            return new ResponseObject(400, e.getMessage(), null);
        }
    }

    @Override
    public ResponseObject editPost(String postId, EditPostDTO editPostDTO, HttpServletRequest req, HttpServletResponse res) throws IllegalAccessException {
        try {
            Post post = getPostByPostId(postId);
            Post editPost = modelMapper.map(editPostDTO, Post.class);
            post.setClosedDate(editPost.getClosedDate());
            post.setCoordinatorName(editPost.getCoordinatorName());
            post.setDocuments(editPostDTO.getDocuments());
            post.setLastUpdateDate(LocalDateTime.now());
            post.setEmail(editPost.getEmail());
            post.setEnrolling(editPost.getEnrolling());
            post.setPostDesc(editPost.getPostDesc());
            post.setPostUrl(editPost.getPostUrl());
            post.setPostWelfare(editPost.getPostWelfare());
            post.setTel(editPost.getTel());
            post.setTitle(editPost.getTitle());
            post.setWorkStartTime(editPost.getWorkStartTime());
            post.setWorkEndTime(editPost.getWorkEndTime());
            post.setWorkDay(editPostDTO.getWorkDay());
            post.setWorkType(editPost.getWorkType());
            addressService.updateAddress(post.getAddress(), editPost.getAddress());
            openPositionService.updateOpenPosition(post, editPost.getOpenPositionList());
            postPositionTagService.updatePostPositionTag(post, editPost.getPostTagListObject());
            postRepository.save(post);
            return new ResponseObject(200, "Edit post id " + postId + " successful.", post);
        } catch (ResponseStatusException e){
            int statusCode= e.getStatus().value();
            res.setStatus(statusCode);
            return new ResponseObject(statusCode, e.getMessage(), null);
        } catch (Exception e) {
            res.setStatus(400);
            return new ResponseObject(400, e.getMessage(), null);
        }
    }

    @Override
    public ResponseObject deletePost(String postId, HttpServletRequest req, HttpServletResponse res) {
        try {
            postRepository.deleteById(postId);
            return new ResponseObject(200, "Delete post id " + postId + " successfully", null);
        } catch (Exception e) {
            res.setStatus(404);
            return new ResponseObject(404, e.getMessage(), null);
        }
    }

}
