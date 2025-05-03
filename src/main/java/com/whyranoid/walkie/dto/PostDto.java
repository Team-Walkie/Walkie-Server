package com.whyranoid.walkie.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.whyranoid.walkie.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {

    @Schema(description = "[응답] 워키 아이디", example = "3")
    private Long viewerId;

    @Schema(description = "[응답] 작성자")
    private WalkieDto poster;

    @Schema(description = "[응답] 게시글 pk", example = "26343")
    private Long postId;

    @Schema(description = "[응답] 좋아요 여부", example = "true")
    private Boolean liked = false;

    @Schema(description = "[응답] 좋아요 누른 유저 리스트")
    private List<WalkieDto> likers = new ArrayList<>();

    @Schema(description = "[응답] 댓글 개수")
    private Integer commentCount;

    @Schema(description = "[응답] 사진파일 URI")
    private String photo;

    @Schema(description = "[응답] 게시글 내용", example = "오운완.")
    private String content;

    @Schema(description = "[응답] 게시 시각", example = "2023-09-09 09:09:09")
    private String date;

    @Schema(description = "[응답] 글씨색 설정", example = "0")
    private Integer colorMode;

    @Schema(description = "[응답] 기록 데이터", example = "2025-02-19 15:49:53_대한민국 서울특별시 관악구 봉천동 1698-1_0.00_00:00:05_0`0``")
    private String historyContent;

    @QueryProjection
    public PostDto(Post post, Long viewerId, List<WalkieDto> liker, Long commentCount) {
        this.viewerId = viewerId;
        this.poster = new WalkieDto(post.getUser());
        this.postId = post.getPostId();
        this.likers = liker.stream()
                .filter(dto -> dto.getWalkieId() != null)  // 또는 Objects::nonNull이면 DTO 자체 null 제거
                .collect(Collectors.toList());
        this.liked = this.likers.stream()
                .map(WalkieDto::getWalkieId)
                .filter(Objects::nonNull)
                .anyMatch(id -> id.equals(viewerId));
        this.commentCount = Math.toIntExact(commentCount != null ? commentCount : 0);
        this.photo = post.getPhoto();
        this.content = post.getContent();
        this.date = post.getDate();
        this.colorMode = post.getColorMode();
        this.historyContent = post.getHistoryContent();
    }

    @QueryProjection
    public PostDto(Post post, Long viewerId, Long commentCount) {
        this.viewerId = viewerId;
        this.poster = new WalkieDto(post.getUser());
        this.postId = post.getPostId();
        this.liked = likers.stream()
                .map(WalkieDto::getWalkieId)
                .filter(Objects::nonNull)
                .anyMatch(id -> id.equals(viewerId));
        this.commentCount = Math.toIntExact(commentCount);
        this.photo = post.getPhoto();
        this.content = post.getContent();
        this.date = post.getDate();
        this.colorMode = post.getColorMode();
        this.historyContent = post.getHistoryContent();
    }
}
