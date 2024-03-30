package com.example.internhub.dtos;

import com.example.internhub.entities.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter @Setter
public class UserGeneralInformationListDTO {
        private Address address;
        private Company company;
        private LocalDateTime createdDate;
        private LocalDate dateOfBirth;
        private ArrayList<Education> educations = new ArrayList<>();
        private ArrayList<Experience> experiences = new ArrayList<>();
        private String email;
        private String firstname;
        private Gender gender;
        private ArrayList<Language> languages = new ArrayList<>();
        private String lastname;
        private LocalDateTime lastActive;
        private LocalDateTime lastUpdate;
        private String password;
        private String phoneNumber;
        private Role role;
        private ArrayList<Skill> skills = new ArrayList<>();
        private String userId;
        private String userDesc;
        private String username;
        private String userProfileKey;


}
