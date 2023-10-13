package com.example.internhub.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {
    @Id
    @Column(name = "postId", nullable = false, length = 36)
    private String id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "createdDate", nullable = false)
    private Instant createdDate;

    @Column(name = "lastUpdate", nullable = false)
    private Instant lastUpdate;

    @Lob
    @Column(name = "totalView", nullable = false)
    private String totalView;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "postDesc", length = 500)
    private String postDesc;

    @Column(name = "postWelfare", length = 500)
    private String postWelfare;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compId")
    private Company comp;

    @Column(name = "workMonth", nullable = false)
    private Integer workMonth;

    @Column(name = "enrolling", nullable = false, length = 500)
    private String enrolling;

    @Column(name = "documents", nullable = false, length = 30)
    private String documents;

    @Column(name = "coordinatorName", nullable = false, length = 100)
    private String coordinatorName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;
}