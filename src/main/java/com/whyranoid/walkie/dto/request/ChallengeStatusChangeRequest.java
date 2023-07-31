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
    @NotNull
    @Schema(example = "3")
    private Long statusId;
    @NotNull
    @Schema(example = "P")
    private Character status;

    @Builder
    public ChallengeStatusChangeRequest(Long statusId, char status) {
        this.statusId = statusId;
        this.status = status;
    }
}
