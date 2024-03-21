package com.example.internhub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "languages")
@Getter @Setter
public class Language {
    @Id
    @Column(name = "languageId", nullable = false, length = 36)
    private String languageId;

    @Column(name = "languageName", nullable = false)
    private String languageName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "userId")
    private User user;
}
