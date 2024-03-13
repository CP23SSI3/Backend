package com.example.internhub.dtos;

import com.example.internhub.entities.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class PostPaginationObject {
    private Address address;
    private LocalDate closedDate;
    private CompanyPaginationObject comp;
    private List<OpenPositionPaginationObject> openPositionList;
    private String postDesc;
    private String postId;
    private List<Object> postTagList;
    private PostStatus status;
    private String title;
}
