package com.example.internhub.entities;

import com.example.internhub.responses.ResponseData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
//@ToString
@NoArgsConstructor @AllArgsConstructor
public class Post extends ResponseData {

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;

    @Column(name = "closedDate")
    private LocalDateTime closedDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "compId", nullable = false)
    private Company comp;

    @Column(name = "coordinatorName", nullable = false, length = 100)
    private String coordinatorName;

    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "documents", nullable = false, length = 100)
    private String documents;

    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @Column(name = "enrolling", nullable = false, length = 500)
    private String enrolling;

    @Column(name = "lastUpdateDate", nullable = false)
    private LocalDateTime lastUpdateDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpenPosition> openPositionList;

    @Column(name = "postDesc", nullable = false, length = 500)
    private String postDesc;

    @Id
    @Column(name = "postId", nullable = false, length = 36)
    private String postId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostPositionTag> postPositionTagList;

    @Column(name = "postUrl")
    private String postUrl;

    @Column(name = "postWelfare", nullable = false, length = 500)
    private String postWelfare;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private PostStatus status;

    @Column(name = "tel", nullable = false, length = 12)
    private String tel;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "totalView", nullable = false, precision = 10)
    private long totalView;

    @Column(name = "workDay", nullable = false, length = 30)
    private String workDay;

    @Column(name = "workEndTime", nullable = false)
    private LocalTime workEndTime;

    @Column(name = "workStartTime", nullable = false)
    private LocalTime workStartTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "workType", nullable = false, length = 20)
    private WorkType workType;

    public void addOpenPosition(OpenPosition openPosition){
        openPositionList.add(openPosition);
        openPosition.setPost(this);
    }
    public String[] getWorkDay(){
        return workDay.split(",");
    }

    public String[] getDocuments(){
        return documents.split(",");
    }

}