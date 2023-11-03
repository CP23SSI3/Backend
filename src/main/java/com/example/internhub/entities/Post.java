package com.example.internhub.entities;

import com.example.internhub.responses.ResponseData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter @Setter
public class Post extends ResponseData {
    @Id
    @Column(name = "postId", nullable = false, length = 36)
    private String postId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "lastUpdateDate", nullable = false)
    private LocalDateTime lastUpdateDate;

    @Column(name = "closedDate")
    private LocalDateTime closedDate;

    @Column(name = "totalView", nullable = false, precision = 10)
    private BigDecimal totalView;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private PostStatus status;

    @Column(name = "postDesc", nullable = false, length = 500)
    private String postDesc;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "compId", nullable = false)
    private Company comp;

    @Column(name = "postWelfare", nullable = false, length = 500)
    private String postWelfare;

    @Column(name = "enrolling", nullable = false, length = 500)
    private String enrolling;

    @Column(name = "documents", nullable = false, length = 100)
    private String documents;

    @Column(name = "coordinatorName", nullable = false, length = 100)
    private String coordinatorName;

    @Column(name = "tel", nullable = false, length = 12)
    private String tel;

    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;

    @Column(name = "workStartTime", nullable = false)
    private LocalTime workStartTime;

    @Column(name = "workEndTime", nullable = false)
    private LocalTime workEndTime;

    @Column(name = "workDay", nullable = false, length = 30)
    private String workDay;

    @Column(name = "workType", nullable = false, length = 20)
    private String workType;

    @OneToMany(mappedBy = "post")
    private List<OpenPosition> openPositionList;

}