package com.whyranoid.walkie.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.whyranoid.walkie.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
    private Boolean liked = false;

    @Schema(description = "[응답] 좋아요 누른 유저 리스트")
    private List<WalkieDto> liker;

    @Schema(description = "[응답] 사진파일 URI")
    private String photo;

    @Schema(description = "[응답] 게시글 내용", example = "오운완.")
    private String content;

    @Schema(description = "[응답] 게시 시각", example = "2023-09-09 09:09:09")
    private String date;

    @Schema(description = "[응답] 글씨색 설정", example = "0")
    private Integer colorMode;

    @Schema(description = "[응답] 기록 데이터")
    private String historyContent;

    @QueryProjection
    public PostDto(Post post, Long viewerId) {
        this.viewerId = viewerId;
        this.posterId = post.getUser().getUserId();
        this.postId = post.getPostId();
        this.liker = post.getLiker().stream().map(WalkieDto::new).collect(Collectors.toList());
        this.photo = post.getPhoto();
        this.content = post.getContent();
        this.date = post.getDate();
        this.colorMode = post.getColorMode();
        this.historyContent = post.getHistoryContent();
    }
}
