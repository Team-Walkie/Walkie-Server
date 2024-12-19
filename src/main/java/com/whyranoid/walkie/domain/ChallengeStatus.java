package com.whyranoid.walkie.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeStatus {

    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walkie_id", nullable = false)
    private Walkie walkie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @NotNull
    @Column(nullable = false)
    private char status = 'N';

    @NotNull
    @Column(nullable = false)
    private Integer progress = 0;

    @NotNull
    @Column(name = "challenge_sdate", length = 19, nullable = false)
    private String challengeSdate;

    @Column(name = "challenge_edate", length = 19)
    private String challengeEdate;

    @NotNull
    @Column(name = "acc_distance", nullable = false)
    private Double accDistance = 0.0;

    @NotNull
    @Column(name = "acc_time", length = 19, nullable = false)
    private String accTime = "00:00:00";

    @NotNull
    @Column(name = "acc_calories", nullable = false)
    private Double accCalories = 0.0;

    @NotNull
    @Column(name = "acc_count", nullable = false)
    private Integer accCount = 0;
}
