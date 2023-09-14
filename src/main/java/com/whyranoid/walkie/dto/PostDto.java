package com.whyranoid.walkie.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {

    @Schema(description = "[응답] 워키 아이디", example = "3")
    private Long viewerId;

    @Schema(description = "[응답] 작성자 아이디", example = "2")
    private Long posterId;

    @Schema(description = "[응답] 게시글 pk", example = "26343")
    private Long postId;

    @Schema(description = "[응답] 좋아요 여부", example = "true")
    private Boolean liked;

    @Schema(description = "[응답] 좋아요 개수", example = "36")
    private Integer likeCount;

    @Schema(description = "[응답] 사진파일 URI", example = "")
    private String photo;

    @Schema(description = "[응답] 게시글 내용", example = "오운완.")
    private String content;

    @Schema(description = "[응답] 게시 시각", example = "2023-09-09 09:09:09")
    private String date;

    @Schema(description = "[응답] 글씨색 설정", example = "0")
    private Integer colorMode;

    @Schema(description = "[응답] 기록 데이터", example = "")
    private String historyContent;
}
