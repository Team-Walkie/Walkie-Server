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
    @JoinColumn(name = "user_id", nullable = false)
    private Walkie walkie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @NotNull
    @Column(nullable = false)
    private char status;

    @NotNull
    @Column(nullable = false)
    private int progress = 0;

}
