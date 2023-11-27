package com.example.internhub.entities;

import com.example.internhub.responses.ResponseData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "openpositions")
@Getter @Setter
@ToString
public class OpenPosition extends ResponseData {

    @Column(name = "openPositionDesc", nullable = false, length = 200)
    private String openPositionDesc;

    @Id
    @Column(name = "openPositionId", nullable = false, length = 36)
    private String openPositionId;

    @Column(name = "openPositionNum", precision = 10, nullable = false)
    private Integer openPositionNum;

    @Column(name = "openPositionTitle", nullable = false, length = 50)
    private String openPositionTitle;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @Column(name = "salary", precision = 10)
    private BigDecimal salary;

//    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
//    @JoinColumn(name = "positionTagId", nullable = false)
//    private PositionTag positionTag;

    @Column(name = "workMonth", nullable = false, precision = 10)
    private Integer workMonth;
}