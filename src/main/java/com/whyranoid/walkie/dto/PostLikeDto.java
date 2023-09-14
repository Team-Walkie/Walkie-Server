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
public class PostLikeDto {

    @Schema(description = "요청 필수 파라미터 - 좋아요 누른 유저의 워키 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Long likerId;

    @Schema(description = "요청 필수 파라미터 - 좋아요 받은 글의 포스트 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "6521")
    private Long postId;

    @Schema(description = "응답 파라미터 - 좋아요 누른 유저 수", example = "62")
    private Long likerCount;

    @Schema(description = "응답 파라미터 - 좋아요 누른 유저들의 프로필")
    private List<WalkieDto> likerProfiles;

    @QueryProjection
    public PostLikeDto(Long likerId, Long postId) {
        this.likerId = likerId;
        this.postId = postId;
    }

    @Builder
    public PostLikeDto(Long likerId, Long postId, Long likerCount, List<WalkieDto> likerProfiles) {
        this.likerId = likerId;
        this.postId = postId;
        this.likerCount = likerCount;
        this.likerProfiles = likerProfiles;
    }

    public void setLikerCount(Long likerCount) {
        this.likerCount = likerCount;
    }
}
