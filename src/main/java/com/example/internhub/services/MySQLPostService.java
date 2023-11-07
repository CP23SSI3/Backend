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
    public Post createPost(CreatePostDTO postDTO) {
        Company company = companyService.getCompanyById("8e20782f-2807-4f13-a11e-0fb9ff955488");
        Post post = modelMapper.map(postDTO, Post.class);
        LocalDateTime now = LocalDateTime.now();
//        post.setPostId(UUID.randomUUID().toString());
        post.setCreatedDate(now);
        post.setLastUpdateDate(now);
        post.setStatus(PostStatus.OPENED);
//        post.getAddress().setAddressId(UUID.randomUUID().toString());
        Address address = addressService.getAddressById("9346a466-4a82-4037-ab00-72ba24fa50bf");
//        Address address2 = modelMapper.map(address, Address.class);
        Address address1 = new Address(address.getAddressId(),
                address.getCountry(), address.getPostalCode(),
                address.getCity(), address.getDistrict(),
                address.getSubDistrict(), address.getArea(),
                address.getLatitude(), address.getLongitude());
        Company comp1 = new Company(company.getCompId(), company.getCompName(),
                company.getCompLogoKey(), company.getCompDesc(),
                company.getDefaultWelfare(), company.getCreatedDate(),
                company.getLastUpdate(), company.getLastActive(),
                company.getCompUrl(), address1);
//        Company company2 = modelMapper.map(company, Company.class);
//        Company company1 = company;
//        Company comp = new Company();
//        comp.setCompId(company.getCompId());
//        comp.setCompName(company.getCompName());
//        comp.setCompLogoKey(company.getCompLogoKey());
//        comp.setCompDesc(company.getCompDesc());

//        comp.setAddress(address);
        post.setComp(comp1);
        return post;
    }
}
