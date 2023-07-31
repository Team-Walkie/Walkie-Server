package com.whyranoid.walkie.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChallengeStatusCreateRequest {
    @NotNull
    @Schema(example = "123")
    private Long userId;

    @NotNull
    @Schema(example = "4")
    private Long challengeId;

    @Builder
    public ChallengeStatusCreateRequest(Long userId, Long challengeId) {
        this.userId = userId;
        this.challengeId = challengeId;
    }
}
