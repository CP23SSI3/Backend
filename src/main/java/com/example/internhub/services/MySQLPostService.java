package com.example.internhub.services;

import com.example.internhub.config.ListMapper;
import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.dtos.EditPostDTO;
import com.example.internhub.dtos.PostPagination;
import com.example.internhub.entities.*;
import com.example.internhub.repositories.PostRepository;
import com.example.internhub.responses.ResponseObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.lang.Math.abs;

@Service
@Primary
public class MySQLPostService implements PostService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper = ListMapper.getInstance();

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

    @Value("${milliseconds.before.closed.post.test}")
    private long milliSecondsBeforeClosedPost;

    Timer timer = new Timer();

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public ResponseObject getAllPostPagination(int pageNumber, int pageSize, String searchText, String city, String district, String status, HttpServletResponse res) {
        try {
            PostStatus postStatus = PostStatus.valueOf(status);
        } catch (Exception e) {
            res.setStatus(400);
            return new ResponseObject(400, e.getMessage(), null);
        }
        Page<Post> postList = postRepository.findByQuery(searchText, city, district, status, PageRequest.of(pageNumber, pageSize));
        PostPagination postPagination = modelMapper.map(postList, PostPagination.class);
        return new ResponseObject(200, "The post's list is successfully sent.",
                postPagination);
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
            LocalDate closedDate = post.getClosedDate();
            LocalDateTime closedDateTime = closedDate.atStartOfDay();
            System.out.println(closedDateTime);
            long milliSecondsBeforeClosed = (closedDate == null) ? 0 : abs(closedDateTime.until(LocalDateTime.now(), ChronoUnit.MILLIS));
            long milliSecondsBeforeNearlyClosed = milliSecondsBeforeClosed - milliSecondsBeforeClosedPost;
            if(closedDate == null) {
                post.alwaysOpenedPost();
            } else if (milliSecondsBeforeNearlyClosed > 0) {
                post.openedPost();
            } else {post.nearlyClosedPost();}
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
            TimerTask nearlyClosedPostTimerTask = new TimerTask() {
                @Override
                public void run() {
                    post.nearlyClosedPost();
                    postRepository.save(post);
                }
            };
            TimerTask closedPostTimerTask =  new TimerTask() {
                @Override
                public void run() {
                    post.closedPost();
                    postRepository.save(post);
                }
            };
            if (milliSecondsBeforeClosed > 0) timer.schedule(closedPostTimerTask, milliSecondsBeforeClosed);
            if (milliSecondsBeforeNearlyClosed > 0) timer.schedule(nearlyClosedPostTimerTask, milliSecondsBeforeNearlyClosed);
            //----------------------------------------

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
