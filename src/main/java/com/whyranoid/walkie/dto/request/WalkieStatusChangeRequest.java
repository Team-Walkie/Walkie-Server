package com.whyranoid.walkie.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkieStatusChangeRequest {

    @NotNull
    private String authId;

    @NotNull
    private Character newStatus;

    @Builder
    public WalkieStatusChangeRequest(String authId, Character newStatus) {
        this.authId = authId;
        this.newStatus = newStatus;
    }
}
