package com.example.internhub.dtos;

import com.example.internhub.entities.Document;
import com.example.internhub.entities.PositionTag;
import com.example.internhub.entities.PostPositionTag;
import com.example.internhub.entities.WorkDay;
import com.example.internhub.services.ArrayStringService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EditPostDTO {
    @JsonIgnore
    LocalDateTime now  = LocalDateTime.now();
    @NotNull
    CreateAddressDTO address;
    LocalDateTime closedDate;
    String coordinatorName;
    @NotNull
    Document[] documents;
    LocalDateTime lastUpdateDate = now;
    String email;
    @NotNull
    String enrolling;
    @NotNull
    @NotNull.List({})
    List<CreateOpenPositionDTO> openPositionList;
    @NotNull
    String postDesc;
    List<String> postTagList;
    String postUrl;
    @NotNull
    String postWelfare;
    String tel;
    @NotNull
    String title;
    @NotNull
    LocalTime workStartTime;
    @NotNull
    LocalTime workEndTime;
    @NotNull
    WorkDay[] workDay;
    @NotNull
    String workType;

    public String getWorkDay() {
        try {
            java.util.List<String> workDayString = new ArrayList<>();
            for (WorkDay day : workDay) {
                workDayString.add(day.name());
            }
            return String.join(",", workDayString);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public String getDocuments() {
        try {
            java.util.List<String> documentString = new ArrayList<>();
            for (Document document : documents) {
                documentString.add(document.name());
            }
            return String.join(",", documentString);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<PostPositionTag> getPostTagList() {
        java.util.List<PostPositionTag> tagList = new ArrayList<>();
        for (String tag : postTagList) {
            PostPositionTag postPositionTag = new PostPositionTag(
                    null, UUID.randomUUID().toString(),
                    new PositionTag(tag)
            );
            tagList.add(postPositionTag);
        }
        return tagList;
    }
}
