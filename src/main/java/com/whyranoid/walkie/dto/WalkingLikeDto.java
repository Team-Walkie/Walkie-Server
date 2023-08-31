package com.whyranoid.walkie.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkingLikeDto {

    @Schema(description = "요청 필수 파라미터 - 좋아요 누른 유저의 워키 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Long senderId;

    @Schema(description = "요청 필수 파라미터 - 좋아요 받은 유저의 워키 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long receiverId;

    @Schema(description = "응답 파라미터 - 좋아요 누른 유저 수", example = "32")
    private Long likerCount;

    @Schema(description = "응답 파라미터 - 좋아요 누른 유저 5명의 프로필")
    private List<WalkieDto> likerProfiles;

    @QueryProjection
    public WalkingLikeDto(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    @Builder
    public WalkingLikeDto(Long receiverId, Long likerCount, List<WalkieDto> likerProfiles) {
        this.receiverId = receiverId;
        this.likerCount = likerCount;
        this.likerProfiles = likerProfiles;
    }
}
