package com.example.internhub.entities;

import com.example.internhub.responses.Object;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
public class User extends Object {

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;
//
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "compId", nullable = true)
    private Company company;

    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "email", length = 320, nullable = false)
    private String email;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;

    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;

    @Column(name = "lastActive", nullable = false)
    private LocalDateTime lastActive;

    @Column(name = "lastUpdate", nullable = false)
    private LocalDateTime lastUpdate;

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

    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

}
