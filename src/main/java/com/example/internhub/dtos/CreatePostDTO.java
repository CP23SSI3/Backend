package com.example.internhub.dtos;

import com.example.internhub.entities.Document;
import com.example.internhub.entities.PositionTag;
import com.example.internhub.entities.PostPositionTag;
import com.example.internhub.entities.WorkDay;
import com.example.internhub.responses.ResponseData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    List<String> postTagList;
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

//    public List<PostPositionTag> getPostTagList() {
//        List<PostPositionTag> postPositionTagList = new ArrayList<>();
//////        System.out.println(postTagList[1]);
//////        System.out.println(new PostPositionTag(null, null,
//////                new PositionTag("Frontend developer")));
//////        postPositionTagList.add(new PostPositionTag(null, null,
//////                new PositionTag("Frontend developer")));
////        for (String tag : postTagList) {
////            System.out.println(tag);
////            System.out.println(new PositionTag(tag));
//////            postPositionTagList.add(new PostPositionTag(tag));
//////            System.out.println(new PostPositionTag(null,null,tag));
////        }
//////        System.out.println(postPositionTagList);
//        return postPositionTagList;
//    }

}
