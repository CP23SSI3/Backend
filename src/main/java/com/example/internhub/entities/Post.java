package com.example.internhub.entities;

import com.example.internhub.responses.Object;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter @Setter
//@ToString
@NoArgsConstructor @AllArgsConstructor
public class Post extends Object {

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

    @Column(name = "email", nullable = false, length = 320)
    private String email;

    @Column(name = "enrolling", nullable = false, length = 1500)
    private String enrolling;

    @Column(name = "lastUpdateDate", nullable = false)
    private LocalDateTime lastUpdateDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpenPosition> openPositionList;

    @Column(name = "postDesc", nullable = false, length = 1500)
    private String postDesc;

    @Id
    @Column(name = "postId", nullable = false, length = 36)
    private String postId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostPositionTag> postTagList;

    @Column(name = "postUrl", length = 255)
    private String postUrl;

    @Column(name = "postWelfare", nullable = false, length = 1500)
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

    public void addPostTag(PostPositionTag postPositionTag){
        postTagList.add(postPositionTag);
        postPositionTag.setPost(this);
    }

    public void addOpenPosition(List<OpenPosition> openPositionList) {
//        this.openPositionList.clear();
//        System.out.println(openPositionList);
        for (OpenPosition position : openPositionList) {
            position.setPost(this);
        }
    }

    public void addPostTag(List<PostPositionTag> postPositionTagList) {
//        this.postTagList.clear();
        for (PostPositionTag tag : postPositionTagList) {
            tag.setPost(this);
        }
    }

    public void clearPostTag() {
        this.postTagList.clear();
    }
    public String[] getWorkDay(){
        return workDay.split(",");
    }

    public String[] getDocuments(){
        return documents.split(",");
    }

    public List<String> getPostTagList() {
        List<String> tagNameList = new LinkedList<>();
        for (PostPositionTag tag: postTagList) {
            tagNameList.add(tag.getPositionTag());
        }
        return tagNameList;
    }

    @JsonIgnore
    public List<PostPositionTag> getPostTagListObject() {
        return this.postTagList;
    }

//    public void setOpenPositionList(List<OpenPosition> list) {
//        this.openPositionList.clear();
//        if (list != null) {
//            for (OpenPosition openPosition : list){
//                this.openPositionList.add(openPosition);
//                openPosition.setPost(this);
//            }
//        }
//    }
}