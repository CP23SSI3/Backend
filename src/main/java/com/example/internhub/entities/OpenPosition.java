package com.example.internhub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "openPositions")
@Getter @Setter
@ToString
public class OpenPosition {

    @Column(name = "openPositionDesc", nullable = false, length = 200)
    private String openPositionDesc;

    @Id
    @Column(name = "openPositionId", nullable = false, length = 36)
    private String openPositionId;

    @Column(name = "openPositionNum")
    private Integer openPositionNum;

    @Column(name = "openPositionTitle", nullable = false, length = 50)
    private String openPositionTitle;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @Column(name = "salary", precision = 10)
    private BigDecimal salary;

    @Column(name = "workMonth")
    private Integer workMonth;
}