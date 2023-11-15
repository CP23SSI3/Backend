package com.example.internhub.dtos;

import com.example.internhub.entities.Document;
import com.example.internhub.entities.WorkDay;
import com.example.internhub.responses.ResponseData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter @ToString
public class CreatePostDTO extends ResponseData {
    CreateAddressDTO address;
    LocalDateTime closedDate;
    CompanyIdDTO comp;
    String coordinatorName;
    Document[] documents;
    String email;
    String enrolling;
    List<CreateOpenPositionDTO> openPositionList;
    String postId = UUID.randomUUID().toString();
    String postDesc;
    String[] postPositionTag;
    String postUrl;
    String postWelfare;
    String tel;
    String title;
    LocalTime workStartTime;
    LocalTime workEndTime;
    WorkDay[] workDay;
    String workType;

    public String getWorkDay() {
        List<String> workDayString = new ArrayList<>();
        for (WorkDay day : workDay) {
            workDayString.add(day.name());
        }
        return String.join(",", workDayString);
    }

    public String getDocuments() {
        List<String> documentString = new ArrayList<>();
        for (Document document : documents) {
            documentString.add(document.name());
        }
        return String.join(",", documentString);
    }

}
