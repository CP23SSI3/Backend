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
    String postId = UUID.randomUUID().toString();
    String title;
    LocalDateTime closedDate;
    String postDesc;
    String postWelfare;
    String enrolling;
    Document[] documents;
    String coordinatorName;
    String tel;
    String email;
    CreateAddressDTO address;
    CompanyIdDTO comp;
    LocalTime workStartTime;
    LocalTime workEndTime;
    WorkDay[] workDay;
    String workType;
    String postUrl;
    List<CreateOpenPositionDTO> openPositionList;

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
