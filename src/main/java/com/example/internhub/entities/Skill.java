package com.example.internhub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "skills")
@Getter @Setter
public class Skill {
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "userId")
    private User user;

    @Id
    @Column(name = "skillId", length = 36)
    private String skillId;

    @Column(name = "skillName", nullable = false, length = 100)
    private String skillName;

    @Column(name = "skillDesc", nullable = false, length = 1500)
    private String skillDesc;
}
