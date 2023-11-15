package com.example.internhub.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "postpositiontags")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Postpositiontag {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @Id
    @Column(name = "postPositionTagId", nullable = false, length = 36)
    private String postPositionTagId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "positionTagName", nullable = false)
    private PositionTag positionTagName;

}