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
    private char category;

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
}
