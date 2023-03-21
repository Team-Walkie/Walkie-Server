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
public class ChallengeStatus {

    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Walkie walkie;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @NotNull
    @Column(nullable = false)
    private char status;

    @NotNull
    @Column(nullable = false)
    private int progress = 0;
}
