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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private AddressService addressService;
    @Autowired
    private OpenPositionService openPositionService;
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
    public Post createPost(CreatePostDTO createPostDTO) {
        try {
            Post post = modelMapper.map(createPostDTO, Post.class);
            LocalDateTime now = LocalDateTime.now();
            post.setCreatedDate(now);
            post.setLastUpdateDate(now);
            post.setStatus(PostStatus.OPENED);
            Company company = companyService.getCompanyById(post.getComp().getCompId());
            post.setComp(companyService.getCompany(company));
            List<OpenPosition> openPositionList = post.getOpenPositionList();
            post.setOpenPositionList(new ArrayList<>());

            for (OpenPosition openPosition : openPositionList) {
                System.out.println(openPosition);
                System.out.println(openPosition.getOpenPositionTitle() + " " + openPosition.getPositionTag().getPositionTagId());
                PositionTag positionTag = positionTagService.getPositionTagById(openPosition.getPositionTag().getPositionTagId());
//            openPosition.setPositionTag(positionTag);
                openPosition.setPositionTag(positionTagService.getPositionTag(positionTag));
                post.addOpenPosition(openPosition);
//            System.out.println(openPosition);
            }

//        addressService.createAddress(createPostDTO.getAddress());
            postRepository.save(post);
//        System.out.println(post.getOpenPositionList().get(0).getPost().getPostId());
            return post;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//    @Override
//    public Post createPost(CreatePostDTO postDTO) {
//        Company company = companyService.getCompanyById("8e20782f-2807-4f13-a11e-0fb9ff955488");
//        Post post = modelMapper.map(postDTO, Post.class);
//        LocalDateTime now = LocalDateTime.now();
//        post.setCreatedDate(now);
//        post.setLastUpdateDate(now);
//        post.setStatus(PostStatus.OPENED);
//        Address address = addressService.getAddressById("9346a466-4a82-4037-ab00-72ba24fa50bf");
//        Address address1 = new Address(address.getAddressId(),
//                address.getCountry(), address.getPostalCode(),
//                address.getCity(), address.getDistrict(),
//                address.getSubDistrict(), address.getArea(),
//                address.getLatitude(), address.getLongitude());
//        Company comp1 = new Company(company.getCompId(), company.getCompName(),
//                company.getCompLogoKey(), company.getCompDesc(),
//                company.getDefaultWelfare(), company.getCreatedDate(),
//                company.getLastUpdate(), company.getLastActive(),
//                company.getCompUrl(), address1);
//        post.setComp(comp1);
////        System.out.println(postDTO.getOpenPositionList().get(0).getOpenPositionId());
////        System.out.println(post.getOpenPositionList().get(0).getOpenPositionId());
//        openPositionRepository.save(post.getOpenPositionList().get(0));
////        addressRepository.save(post.getAddress());
////        postRepository.save(post);
////        openPositionRepository.save(post.getOpenPositionList().get(0));
//        return post;
//    }
}
