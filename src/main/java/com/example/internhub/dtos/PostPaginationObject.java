package com.example.internhub.dtos;

import com.example.internhub.entities.Company;
import com.example.internhub.entities.OpenPosition;
import com.example.internhub.entities.PostPositionTag;
import com.example.internhub.entities.PostStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class PostPaginationObject {
    private String postId;
    private LocalDate closedDate;
    private CompanyPaginationObject comp;
    private List<OpenPositionPaginationObject> openPositionList;
    private String postDesc;
    private List<Object> postTagList;
    private PostStatus status;
    private String title;
}
