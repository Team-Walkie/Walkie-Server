package com.whyranoid.walkie.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChallengeStatusCreateRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long challengeId;

    @Builder
    public ChallengeStatusCreateRequest(Long userId, Long challengeId) {
        this.userId = userId;
        this.challengeId = challengeId;
    }
}
