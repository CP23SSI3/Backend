package com.example.internhub.services;

import com.example.internhub.dtos.CreatePostDTO;
import com.example.internhub.dtos.PostPagination;
import com.example.internhub.entities.Address;
import com.example.internhub.entities.Company;
import com.example.internhub.entities.Post;
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
//        addressService.createAddress(createPostDTO.getAddress());
//        companyService.createCompany();
        Post post = modelMapper.map(createPostDTO, Post.class);
        System.out.println(post.getComp());
        System.out.println(post.getComp().getAddress());
        Company company = companyService.getCompanyById(post.getComp().getCompId());
//        Address address = new Address();
//        address.setAddressId("a");
//        post.setAddress(address);
//        System.out.println(company.getCompName());
//        post.setComp(company);
        company.setAddress(null);
        post.setComp(company);
        System.out.println(post.getComp());
//        Address address = addressService.getAddressById("9346a466-4a82-4037-ab00-72ba24fa50bf");
//
//        Address address1 = new Address(address.getAddressId(),
//                address.getCountry(), address.getPostalCode(),
//                address.getCity(), address.getDistrict(),
//                address.getSubDistrict(), address.getArea(),
//                address.getLatitude(), address.getLongitude());
//
//        Company comp1 = new Company(company.getCompId(), company.getCompName(),
//                company.getCompLogoKey(), company.getCompDesc(),
//                company.getDefaultWelfare(), company.getCreatedDate(),
//                company.getLastUpdate(), company.getLastActive(),
//                company.getCompUrl(), address1);

//        post.setComp(comp1);

//        post.setComp(companyService.getCompanyById(createPostDTO.getComp().getCompId()));
//        openPositionService.createOpenPosition(postDTO.getOpenPositionList().get(0));
        return post;
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
