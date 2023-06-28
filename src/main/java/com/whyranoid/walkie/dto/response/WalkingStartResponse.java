package com.whyranoid.walkie.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkingStartResponse {

    @NotNull
    private Long historyId;

    @NotNull
    private WalkieStatusChangeResponse walkieStatusChangeResponse;

    @Builder
    public WalkingStartResponse(Long historyId, WalkieStatusChangeResponse walkieStatusChangeResponse) {
        this.historyId = historyId;
        this.walkieStatusChangeResponse = walkieStatusChangeResponse;
    }
}
