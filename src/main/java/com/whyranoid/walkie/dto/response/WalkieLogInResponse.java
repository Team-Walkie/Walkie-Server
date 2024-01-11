package com.whyranoid.walkie.dto.response;

import com.whyranoid.walkie.domain.Walkie;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WalkieLogInResponse {

    @Schema(description = "가입된 사용자 고유 키 -- 사용자 조회 시 사용")
    private Long walkieId;

    @Schema(description = "가입된 사용자 닉네임")
    private String nickname;

    @Schema(description = "프로필 이미지 URI")
    private String profileImg;

    public WalkieLogInResponse(Walkie walkie) {
        this.walkieId = walkie.getUserId();
        this.nickname = walkie.getUserName();
        this.profileImg = walkie.getProfileImg();
    }
}
