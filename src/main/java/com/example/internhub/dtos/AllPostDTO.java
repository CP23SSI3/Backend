package com.example.internhub.dtos;

import com.example.internhub.entities.Address;
import com.example.internhub.entities.Company;
import com.example.internhub.entities.OpenPosition;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AllPostDTO {
    String postId;
    String title;
    Company comp;
    Address address;
    List<OpenPosition> openPositionList;
}
