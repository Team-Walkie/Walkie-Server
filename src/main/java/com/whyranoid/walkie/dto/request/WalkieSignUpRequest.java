package com.whyranoid.walkie.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkieSignUpRequest {

    @NotNull
    private String userName;

    private String profileImg;

    @NotNull
    private String authId;

    @NotNull
    private Boolean agreeGps;

    @NotNull
    private Boolean agreeSubscription;

    @Builder
    public WalkieSignUpRequest(String userName, String profileImg, String authId, Boolean agreeGps, Boolean agreeSubscription) {
        this.userName = userName;
        this.profileImg = profileImg;
        this.authId = authId;
        this.agreeGps = agreeGps;
        this.agreeSubscription = agreeSubscription;
    }
}
