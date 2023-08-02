package com.whyranoid.walkie.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WalkieSignUpResponse {

    @Schema(description = "요청 후 중복 발생한 경우 true 반환")
    private boolean hasDuplicatedName;
    
    @Schema(description = "가입된 사용자 고유 키 -- 사용자 조회 시 사용")
    private Long walkieId;
    
    @Schema(description = "(검증용) 가입 요청했던 닉네임 반환")
    private String walkieName;

//    private String accessToken;
//    private String refreshToken;
}
