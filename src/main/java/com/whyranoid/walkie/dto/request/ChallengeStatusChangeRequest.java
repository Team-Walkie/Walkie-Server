package com.whyranoid.walkie.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChallengeStatusChangeRequest {
    @NotNull
    private Long statusId;
    @NotNull
    private Character status;

    @Builder
    public ChallengeStatusChangeRequest(Long statusId, char status) {
        this.statusId = statusId;
        this.status = status;
    }
}
