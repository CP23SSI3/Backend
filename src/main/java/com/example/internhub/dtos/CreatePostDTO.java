package com.example.internhub.dtos;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.entities.PostPositionTag;
import com.example.internhub.entities.WorkType;
import com.example.internhub.services.ArrayStringService;
import com.example.internhub.validators.EnumDocumentTypesConstraint;
import com.example.internhub.validators.EnumWorkDayConstraint;
import com.example.internhub.validators.EnumWorkTypeConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class CreatePostDTO {
//    @NotNull(message = "Address is required.")
    @Valid
    CreateAddressDTO address;
    @Future(message = "Closed date must be in future.")
    LocalDate closedDate;
    @Valid
    @NotNull(message = "Company id must be provide.")
    CompanyIdDTO comp;
    @NotNull(message = "Coordinator name is required.")
    @Size(max = 100, message = "Coordinator name is too long, 100 characters maximum.")
    String coordinatorName;
    @EnumDocumentTypesConstraint
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
    @Valid
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
//    boolean sameAddressAsCompany;
    @NotNull(message = "Work start time is required.")
    LocalTime workStartTime;
    @NotNull(message = "Work end time is required.")
    LocalTime workEndTime;
    @NotNull(message = "Working day must be provided.")
    @NotEmpty(message = "Post must have at least one working day.")
    @EnumWorkDayConstraint
    List<String> workDay;
    @NotNull(message = "Work type is required.")
    @EnumWorkTypeConstraint
    String workType;

    @JsonIgnore
    ArrayStringService arrayStringService = new ArrayStringService();

    public String getWorkDay() {
        return arrayStringService.getStringFromWorkDayArray(workDay);
    }

    public String getDocuments() {
        return arrayStringService.getStringFromDocumentArray(documents);
    }

    public WorkType getWorktype() {
        return WorkType.valueOf(workType.toUpperCase());
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

//    public boolean getSameAddressAsCompany() {
//        return this.sameAddressAsCompany;
//    }

}
