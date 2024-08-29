package com.whyranoid.walkie.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class MyInfoRequest {
    @Schema(description = "변경하고자 하는 프로필 이미지. null로 요청하는 경우 기본이미지 링크로 변경됨", example = "seungmin_profile_img")
    private String profileImg;

    @Schema(description = "변경하고자 하는 닉네임", example = "주용한바보")
    private String nickname;

    @Schema(description = "변경하고자 하는 실명", example = "홍길동")
    private String name;

    @Builder
    MyInfoRequest(String profileImg, String nickname, String name) {
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.name = name;
    }
}
