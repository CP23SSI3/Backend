package com.example.internhub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "postpositiontags")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class PostPositionTag {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @Id
    @Column(name = "postPositionTagId", nullable = false, length = 36)
    private String postPositionTagId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "positionTagName", nullable = false)
    private PositionTag positionTag;

//    public PostPositionTag(String positionTagName) {
//        new PostPositionTag(null, UUID.randomUUID().toString(), new PositionTag(positionTagName));
//    }
//
//    public PostPositionTag(PositionTag positionTag) {
//        new PostPositionTag(null, UUID.randomUUID().toString(), positionTag);
//    }

    public String getPositionTag() {
        return positionTag.getPositionTagName();
    }



}