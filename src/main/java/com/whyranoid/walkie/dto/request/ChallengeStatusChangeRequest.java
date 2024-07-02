package com.whyranoid.walkie.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChallengeStatusChangeRequest {
    @Schema(example = "3")
    private Long walkieId;
    @Schema(example = "3")
    private Long challengeId;
    @Schema(example = "P")
    private Character status;

    @Builder
    public ChallengeStatusChangeRequest(Long walkieId, Long challengeId, Character status) {
        this.walkieId = walkieId;
        this.challengeId = challengeId;
        this.status = status;
    }
}
