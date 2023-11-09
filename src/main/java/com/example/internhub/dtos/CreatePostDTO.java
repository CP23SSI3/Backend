package com.example.internhub.dtos;

import com.example.internhub.entities.Company;
import com.example.internhub.responses.ResponseData;
import com.example.internhub.services.CompanyService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

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
    String documents;
    String coordinatorName;
    String tel;
    String email;
    CreateAddressPostDTO address;
//    Company comp = companyService.getCompanyById("8e20782f-2807-4f13-a11e-0fb9ff955488");
    LocalTime workStartTime;
    LocalTime workEndTime;
    String workDay;
    String workType;
    List<CreateOpenPositionDTO> openPositionList;
}
