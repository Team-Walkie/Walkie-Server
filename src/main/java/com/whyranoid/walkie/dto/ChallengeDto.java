package com.whyranoid.walkie.dto;

import com.whyranoid.walkie.domain.Badge;
import lombok.*;

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
    private char status;
    private int progress;
}
