package com.whyranoid.walkie.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkieStatusChangeResponse {

    @NotNull
    private Boolean updateSuccess;

    private Character currentStatus;

    @Builder
    public WalkieStatusChangeResponse(Boolean updateSuccess, Character currentStatus) {
        this.updateSuccess = updateSuccess;
        this.currentStatus = currentStatus;
    }
}
