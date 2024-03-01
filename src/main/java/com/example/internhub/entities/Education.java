package com.example.internhub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Year;

@Entity
@Table(name = "educations")
@Getter @Setter
public class Education {
    @Column(name = "degree", nullable = false)
    private String degree;

    @Column(name = "educationDesc",length = 1500)
    private String educationDesc;

    @Id
    @Column(name = "educationId", nullable = false, length = 36)
    private String educationId;

    @Column(name = "field", length = 100)
    private String field;

    @Column(name = "grade", length = 5)
    private String grade;

    @Column(name = "graduatedYear", nullable = false)
    private int graduatedYear;

    @Column(name = "schoolName", nullable = false, length = 100)
    private String schoolName;

    @Column(name = "startedYear")
    private int startedYear;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "userId")
    private User user;
}
