package com.whyranoid.walkie.dto;

import com.whyranoid.walkie.domain.Badge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeDto {
    private Long challengeId;
    private char category;
    private Badge badge;
    private String content;
    private String name;
    private String img;

    // 여기서부터 challenge_status;
    private char status;
    private int progress;
}
