package com.whyranoid.walkie.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Badge {

    @Id
    @Column(name = "badge_id", nullable = false)
    @Schema(example = "2")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long badgeId;

    @NotNull
    @Schema(example = "badge_image2")
    @Column(name = "badge_img", nullable = false)
    private String img;

    @NotNull
    @Schema(example = "badge_image2")
    @Column(name = "badge_failure_img")
    private String failureImg;

    @NotNull
    @Schema(example = "햄버거 세트 배지")
    @Column(name = "badge_name", nullable = false)
    private String badgeName;
}
