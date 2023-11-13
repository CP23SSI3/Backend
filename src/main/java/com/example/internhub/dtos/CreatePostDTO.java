package com.example.internhub.dtos;

import com.example.internhub.responses.ResponseData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter @ToString
public class CreatePostDTO extends ResponseData {
    String postId = UUID.randomUUID().toString();
    String title;
    LocalDateTime closedDate;
    String postDesc;
    String postWelfare;
    String enrolling;
    String[] documents;
    String coordinatorName;
    String tel;
    String email;
    CreateAddressDTO address;
    CompanyIdDTO comp;
    LocalTime workStartTime;
    LocalTime workEndTime;
    String[] workDay;
    String workType;
    List<CreateOpenPositionDTO> openPositionList;

    public String getWorkDay() {
        return String.join(",", workDay);
    }

    public String getDocuments() {
        return String.join(",", documents);
    }
}
