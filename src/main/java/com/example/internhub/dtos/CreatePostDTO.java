package com.example.internhub.dtos;

import com.example.internhub.entities.Document;
import com.example.internhub.entities.PositionTag;
import com.example.internhub.entities.PostPositionTag;
import com.example.internhub.entities.WorkDay;
import com.example.internhub.responses.Object;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter @ToString
public class CreatePostDTO extends Object {
    @NotNull
    CreateAddressDTO address;
    @Future
    LocalDateTime closedDate;
    @NotNull
    CompanyIdDTO comp;
    @NotNull @Size(max = 100)
    String coordinatorName;
    @NotNull
    Document[] documents;
    @NotNull @Size(max = 320) @Email
    String email;
    @NotNull @Size(max = 1500)
    String enrolling;
    @NotNull.List({})
    List<CreateOpenPositionDTO> openPositionList;
    String postId = UUID.randomUUID().toString();
    @NotNull @Size(max = 1500)
    String postDesc;
    List<String> postTagList;
    @Size(max = 255)
    String postUrl;
    @NotNull @Size(max = 1500)
    String postWelfare;
    @NotNull @Size(max = 12)
    String tel;
    @NotNull @Size(max = 100)
    String title;
    @NotNull
    LocalTime workStartTime;
    @NotNull
    LocalTime workEndTime;
    @NotNull @NotNull.List({})
    WorkDay[] workDay;
    @NotNull
    String workType;

    public String getWorkDay() {
        try {
            List<String> workDayString = new ArrayList<>();
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
            List<String> documentString = new ArrayList<>();
            for (Document document : documents) {
                documentString.add(document.name());
            }
            return String.join(",", documentString);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<PostPositionTag> getPostTagList() {
        List<PostPositionTag> tagList = new ArrayList<>();
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
