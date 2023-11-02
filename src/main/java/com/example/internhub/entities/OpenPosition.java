package com.example.internhub.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "openpositions")
@Getter @Setter
public class OpenPosition {
    @Id
    @Column(name = "openPositionId", nullable = false, length = 36)
    private String openPositionId;

    @Column(name = "openPositionTitle", nullable = false, length = 50)
    private String openPositionTitle;

    @Column(name = "openPositionNum", nullable = false, precision = 10)
    private BigDecimal openPositionNum;

    @Column(name = "openPositionDesc", nullable = false, length = 300)
    private String openPositionDesc;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "positionTagId", nullable = false)
    private PositionTag positionTag;

    @Column(name = "workMonth", nullable = false, precision = 10)
    private BigDecimal workMonth;

    @Column(name = "salary", precision = 10)
    private BigDecimal salary;
}