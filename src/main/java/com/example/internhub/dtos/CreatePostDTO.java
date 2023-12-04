package com.example.internhub.dtos;

import com.example.internhub.entities.Document;
import com.example.internhub.entities.PositionTag;
import com.example.internhub.entities.PostPositionTag;
import com.example.internhub.entities.WorkDay;
import com.example.internhub.responses.Object;
import com.example.internhub.validators.EnumDocumentTypesConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class CreatePostDTO extends Object {
    @NotNull(message = "Address is required.")
    CreateAddressDTO address;
    @Future(message = "Closed date must be in future.")
    LocalDateTime closedDate;
    @Valid
    @NotNull(message = "Company id must be provide.")
    CompanyIdDTO comp;
    @NotNull(message = "Coordinator name is required.")
    @Size(max = 100, message = "Coordinator name is too long, 100 characters maximum.")
    String coordinatorName;
    @EnumDocumentTypesConstraint(message = "Unknown documents type.")
    List<String> documents;
    @NotNull(message = "Email is required.")
    @Size(max = 320, message = "Email is too long, 320 characters maximum.")
    @Email(message = "Bad email format")
    String email;
    @NotNull(message = "Enrolling data is required.")
    @Size(max = 1500, message = "Enrolling data is too long, 1500 characters maximum.")
    String enrolling;
    @NotNull(message = "At least one open position must be provided.")
    @NotEmpty(message = "Post must have at least one open position.")
    List<CreateOpenPositionDTO> openPositionList;
    String postId = UUID.randomUUID().toString();
    @NotNull(message = "Post's description is required.")
    @Size(max = 1500, message = "Post's description is too long, 1500 characters maximum.")
    String postDesc;
    List<String> postTagList;
    @Size(max = 255, message = "Post's url is too long, 255 characters maximum.")
    String postUrl;
    @NotNull(message = "Post's welfare is required.")
    @Size(max = 1500, message = "Post's welfare is too long, 1500 characters maximum.")
    String postWelfare;
    @NotNull(message = "Telephone number is required.")
    @Size(max = 12, message = "Telephone's number is too long, 12 character's maximum.")
    String tel;
    @NotNull(message = "Post's title is required.")
    @Size(max = 100, message = "Post's title is too long, 100 characters maximum.")
    String title;
    @NotNull(message = "Work start time is required.")
    LocalTime workStartTime;
    @NotNull(message = "Work end time is required.")
    LocalTime workEndTime;
    @NotNull(message = "Working day must be provided.")
    @NotEmpty(message = "Post must have at least one working day.")
    WorkDay[] workDay;
    @NotNull(message = "Work type is required.")
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
            if(documents==null || documents.size() == 0) return null;
            for (String document : documents) {
                Document.valueOf(document);
            }
            return String.join(",", documents);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown data for document types' enum");
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
