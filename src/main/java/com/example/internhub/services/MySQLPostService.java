package com.example.internhub.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.internhub.config.ListMapper;
import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.dtos.EditPostDTO;
import com.example.internhub.dtos.PostPagination;
import com.example.internhub.entities.*;
import com.example.internhub.exception.*;
import com.example.internhub.repositories.PostRepository;
import com.example.internhub.responses.BadRequestResponseEntity;
import com.example.internhub.responses.ForbiddenResponseEntity;
import com.example.internhub.responses.NotFoundResponseEntity;
import com.example.internhub.responses.ResponseObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    private AddressService addressService;
    @Autowired
    private AuthService authService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private OpenPositionService openPositionService;
    @Autowired
    private PostPositionTagService postPositionTagService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    @Value("${milliseconds.before.closed.post}")
    private long milliSecondsBeforeClosedPost;
    @Value("${convert.day.to.minute}")
    private long convertDayToMinute;
    Timer timer = new Timer();


    private void checkIfCompanyCanModifyPost(HttpServletRequest req, String checkCompId) throws CompanyModifyPostException {
        String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        DecodedJWT token = authService.decodeBearerToken(authorizationHeader);
        if (!token.getClaim("role").asString().equals(Role.ADMIN.toString())) {
            User user = userService.findUserByUserName(token.getSubject());
            if (!user.getCompany().getCompId().equals(checkCompId)) throw new CompanyModifyPostException();
        }
    }

    @Override
    public ResponseEntity createPost(CreatePostDTO createPostDTO, HttpServletRequest req, HttpServletResponse res) {
        try {
            checkIfCompanyCanModifyPost(req, createPostDTO.getComp().getCompId());
            Post post = modelMapper.map(createPostDTO, Post.class);
            if (post.getOpenPositionList().size() == 0) {
                throw new EmptyPositionListException();
            }
            LocalDateTime now = LocalDateTime.now();
            post.setCreatedDate(now);
            post.setLastUpdateDate(now);
            LocalDate closedDate = post.getClosedDate();
            long milliSecondsBeforeClosed = (closedDate == null) ? 0 : abs((closedDate.atStartOfDay().plusDays(1)).until(LocalDateTime.now(), ChronoUnit.MILLIS))/convertDayToMinute;
            long milliSecondsBeforeNearlyClosed = milliSecondsBeforeClosed - milliSecondsBeforeClosedPost;
            if(closedDate == null) {
                post.alwaysOpenedPost();
            } else if (milliSecondsBeforeNearlyClosed > 0) {
                post.openedPost();
            } else {post.nearlyClosedPost();}
            Company company = companyService.getCompanyByCompanyId(post.getComp().getCompId());
            post.setComp(company);
            if (createPostDTO.getAddress() == null) post.setAddress(company.getAddress());
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
            return new ResponseEntity(new ResponseObject(200, "Create post successfully.", post),
                    null, HttpStatus.OK);
        } catch (EmptyPositionListException ex) {
            return new BadRequestResponseEntity(ex);
        } catch (CompanyModifyPostException ex) {
            return new ForbiddenResponseEntity(ex);
        } catch (CompNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
        }
    }

    @Override
    public ResponseEntity deletePost(String postId, HttpServletRequest req, HttpServletResponse res) {
        try {
            checkIfCompanyCanModifyPost(req, postId);
            getPostByPostId(postId);
            postRepository.deleteById(postId);
            return new ResponseEntity(new ResponseObject(200, "Delete post id " + postId + " successfully", null),
                    null, HttpStatus.OK);
        } catch (PostNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }

    @Override
    public ResponseEntity editPost(String postId, EditPostDTO editPostDTO, HttpServletRequest req, HttpServletResponse res) throws IllegalAccessException {
        try {
            Post post = getPostByPostId(postId);
            checkIfCompanyCanModifyPost(req, post.getComp().getCompId());
            Post editPost = modelMapper.map(editPostDTO, Post.class);
            boolean sameAddressAsComp = post.getAddress().getAddressId().equals(post.getComp().getAddress().getAddressId());
            boolean changedToCompanyAddress = editPostDTO.getAddress() == null &&
                    !post.getAddress().getAddressId().equals(post.getComp().getAddress().getAddressId());
            Address address = post.getAddress();
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
            if (editPostDTO.getAddress() == null && !sameAddressAsComp) {
                post.setAddress(post.getComp().getAddress());
            } else if (editPostDTO.getAddress() != null && !sameAddressAsComp ) {
                addressService.updateAddress(post.getAddress(), editPost.getAddress());
            } else if (editPostDTO.getAddress() != null && sameAddressAsComp) {
                post.setAddress(editPost.getAddress());
            }
            openPositionService.updateOpenPosition(post, editPost.getOpenPositionList());
            postPositionTagService.updatePostPositionTag(post, editPost.getPostTagListObject());
            postRepository.save(post);
            if (editPostDTO.getAddress() == null && sameAddressAsComp) {addressService.deleteAddress(address);}
            return new ResponseEntity(new ResponseObject(200, "Edit post id " + postId + " successful.", post),
                    null, HttpStatus.OK);
        } catch (PostNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (CompanyModifyPostException ex) {
            return new ForbiddenResponseEntity(ex);
        } catch (EmptyPositionListException ex) {
            return new BadRequestResponseEntity(ex);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
        }
    }


    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public ResponseEntity getAllPostPagination(int pageNumber, int pageSize, String searchText, String city, String district,
                                               String[] status, String[] tags, Integer month, Integer salary,
                                               HttpServletResponse res) {
        String[] defaultStatus = {"OPENED", "ALWAYS_OPENED", "NEARLY_CLOSED"};
        List<String> postTag = new ArrayList<>();
        try {
            if (status == null) {
                status = defaultStatus;
            } else {
                for (String s : status) {
                    PostStatus.valueOf(s);
                }
            }
            if (tags != null) {
                if (tags.length == 0) {
                    tags = null;
                } else {
                    for (String tag : tags) {
                        postTag.add(URLDecoder.decode(tag, StandardCharsets.UTF_8.toString()));
                    }
                }
            }

            Page<Post> postList = postRepository.findByQuery(searchText, city, district,
                    status, postTag, tags, month, salary,
                    PageRequest.of(pageNumber, pageSize));
            PostPagination postPagination = modelMapper.map(postList, PostPagination.class);
            return new ResponseEntity(new ResponseObject(200, "The post's list is successfully sent.",
                    postPagination),
                    null, HttpStatus.OK);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
        }
    }

    @Override
    public ResponseEntity getPostById(String postId, HttpServletResponse res) {
        try {
            Post post = getPostByPostId(postId);
            post.view();
            postRepository.save(post);
            return new ResponseEntity(new ResponseObject(200, "The post is successfully sent.", post),
                    null, HttpStatus.OK);
        } catch (PostNotFoundException ex) {
          return new NotFoundResponseEntity(ex);
        } catch (Exception e) {
            return new BadRequestResponseEntity(e);
        }
    }

    @Override
    public ResponseEntity getPostByCompanyPagination(int page, int pageSize, String compId) {
        try {
            Company company = companyService.getCompanyByCompanyId(compId);
            Page<Post> postPage = postRepository.getPostByComp(company, PageRequest.of(page, pageSize));
            PostPagination postPagination = modelMapper.map(postPage, PostPagination.class);
            return new ResponseEntity(new ResponseObject(200, "Posts by company id is successfully sent.", postPagination),
                    null, HttpStatus.OK);
        } catch (CompNotFoundException ex) {
            return new NotFoundResponseEntity(ex);
        } catch (Exception ex) {
            return new BadRequestResponseEntity(ex);
        }
    }


    @Override
    public Post getPostByPostId(String postId) throws PostNotFoundException {
        try {
            return postRepository.findById(postId).orElseThrow();
        } catch (Exception e) {
            throw new PostNotFoundException(postId);
        }
    }

    @Override
    public void postCheck() {
        List<Post> postList = postRepository.findAll();
        for (Post post : postList) {
//            switch (post.getStatus()) {
//                case ALWAYS_OPENED, CLOSED, DELETED : break;
//                case NEARLY_CLOSED: {
//                    LocalDate closedDate = post.getClosedDate();
//                    LocalDate now = LocalDate.now();
//                    if(closedDate.isBefore(now)) {
//                        // post already closed
//                        post.setStatus(PostStatus.CLOSED);
//                    } else {
//                        // post still not closed
//                        // need nearly closed timer
//                    }
//                }
//                case OPENED: {
//                    LocalDate closedDate = post.getClosedDate();
//                    LocalDate now = LocalDate.now();
//                    if(closedDate.isBefore(now)) {
//                        // post already closed
//                        post.setStatus(PostStatus.CLOSED);
//                    }
//                }
//            }
        }
    }


}
