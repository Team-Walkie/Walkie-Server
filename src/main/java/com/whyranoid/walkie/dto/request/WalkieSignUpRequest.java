package com.whyranoid.walkie.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkieSignUpRequest {

    @NotNull
    @Schema(name = "userName", requiredMode = Schema.RequiredMode.REQUIRED, description = "닉네임", example = "군자동 불주먹")
    private String userName;

    @Schema(name = "profileImg", description = "프로필 사진 URI")
    private String profileImg;

    @NotNull
    @Schema(name = "authId", requiredMode = Schema.RequiredMode.REQUIRED, description = "구글 로그인 UID", example = "aslks4283wd-asdjk23oitwdfj")
    private String authId;

    @NotNull
    @Schema(name = "agreeGps", requiredMode = Schema.RequiredMode.REQUIRED, description = "위치 정보 사용 동의", example = "true")
    private Boolean agreeGps;

    @NotNull
    @Schema(name = "agreeSubscription", requiredMode = Schema.RequiredMode.REQUIRED, description = "마케팅 정보 수신 동의", example = "false")
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
