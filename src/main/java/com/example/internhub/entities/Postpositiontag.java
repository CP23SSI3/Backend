package com.example.internhub.entities;

import javax.persistence.*;

@Entity
@Table(name = "postpositiontags")
public class Postpositiontag {
    @Id
    @Column(name = "postPositionTagId", nullable = false, length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "positionTagName", nullable = false)
    private PositionTag positionTagName;

    public PositionTag getPositionTagName() {
        return positionTagName;
    }

    public void setPositionTagName(PositionTag positionTagName) {
        this.positionTagName = positionTagName;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}