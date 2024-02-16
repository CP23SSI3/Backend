package com.example.internhub.entities;

import antlr.collections.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", nullable = true)
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "compId", nullable = true)
    private Company company;

    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "dateOfBirth", nullable = false)
    private LocalDate dateOfBirth;

//    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Education> educations = new LinkedHashSet<>();

    @Column(name = "email", length = 320, nullable = false)
    private String email;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 1)
    private Gender gender;

    @OneToMany(mappedBy = "user")
    private Set<Language> languages = new LinkedHashSet<>();

    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;

    @Column(name = "lastActive", nullable = false)
    private LocalDateTime lastActive;

    @Column(name = "lastUpdate", nullable = false)
    private LocalDateTime lastUpdate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 72, nullable = false)
    private String password;

    @Column(name = "phoneNumber", length = 10, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 7, nullable = false)
    private Role role;

    @Id
    @Column(name = "userId", length = 36 ,nullable = false)
    private String userId;

    @Column(name = "userDesc", length = 1500, nullable = false)
    private String userDesc;

    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @Column(name = "userProfileKey", length = 100)
    private String userProfileKey;

}
