package com.whyranoid.walkie.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeDto {
    Long challengeId;

    char category;

    String badgeImg;
    String challengeImg;
    String name;
    String content;
}
