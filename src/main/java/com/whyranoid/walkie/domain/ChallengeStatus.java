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

    @Column(name = "challenge_sdate", length = 19)
    private String challengeSdate;

    @Column(name = "challenge_edate", length = 19)
    private String challengeEdate;

    @Column(name = "acc_distance")
    private Double accDistance;

    @Column(name = "acc_time", length = 19)
    private String accTime;

    @Column(name = "acc_calories")
    private Double accCalories;

    @Column(name = "acc_count")
    private Integer accCount;
}
