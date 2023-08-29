package com.whyranoid.walkie.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyInfoResponse {
    @Schema(description = "나의 프로필 이미지")
    private String profileImg;

    @Schema(description = "나의 닉네임")
    private String nickname;
}
