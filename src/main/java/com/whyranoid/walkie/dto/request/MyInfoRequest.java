package com.whyranoid.walkie.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class MyInfoRequest {
    @Schema(description = "변경하고자 하는 프로필 이미지", example = "seungmin_profile_img")
    private String profileImg;

    @Schema(description = "변경하고자 하는 닉네임", example = "주용한바보")
    private String nickname;

    @Builder
    MyInfoRequest(String profileImg, String nickname) {
        this.profileImg = profileImg;
        this.nickname = nickname;
    }
}
