package com.example.internhub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "experiences")
@Getter @Setter
public class Experience {

    @Column(name = "compName", length = 100)
    private String compName;

    @Column(name = "endedYear")
    private int endedYear;

    @Id
    @Column(name = "experienceId", length = 36)
    private String experienceId;

    @Column(name = "experienceName", length = 100)
    private String experienceName;

    @Column(name = "experienceDesc", length = 1500)
    private String experienceDesc;

    @Column(name = "position", length = 100)
    private String position;

    @Column(name = "startedYear")
    private int startedYear;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "userId")
    private User user;
}
