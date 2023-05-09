package com.whyranoid.walkie.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WalkieSignUpResponse {

    private boolean hasDuplicatedName;
    private String accessToken;
    private String refreshToken;
}
