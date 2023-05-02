package com.whyranoid.walkie.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class WalkieSignUpRequest {

    @NotNull
    private String userName;

    private String profileImg;

    @NotNull
    private String authId;
}
