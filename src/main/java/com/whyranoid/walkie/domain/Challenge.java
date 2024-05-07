package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {

    @Id
    @Column(name = "challenge_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeId;

    @NotNull
    @Column(nullable = false)
    private char category;  // { C: 칼로리, T: 시간대, D: 거리 }

    // 외래키기라서 데이터는 배지를 먼저 추가한 후에 챌린지를 추가해야 함
    @NotNull
    @OneToOne
    @JoinColumn(name = "badge_id", nullable = false)
    private Badge badge;

    @NotNull
    @Column(nullable = false)
    private String content;

    @NotNull
    @Column(name = "challenge_name", nullable = false)
    private String name;

    @Column(name = "challenge_img")
    private String img;

    @Column(name = "period")
    private Integer period;

    @Column(name = "start_value")
    private Integer startValue;

    @Column(name = "end_value")
    private Integer endValue;

    @Column(name = "new_flag")
    private Integer newFlag;
}