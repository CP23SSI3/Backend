package com.example.internhub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "postpositiontags")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class PostPositionTag {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "postId", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Post post;

    @Id
    @Column(name = "postPositionTagId", nullable = false, length = 36)
    private String postPositionTagId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "positionTagName", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private PositionTag positionTag;

    public String getPositionTag() {
        return positionTag.getPositionTagName();
    }



}