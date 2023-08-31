package com.whyranoid.walkie.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkingLikeDto {

    @Schema(description = "요청 필수 파라미터 - 좋아요 누른 유저의 워키 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Long senderId;

    @Schema(description = "요청 필수 파라미터 - 좋아요 받은 유저의 워키 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long receiverId;

    @QueryProjection
    public WalkingLikeDto(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
