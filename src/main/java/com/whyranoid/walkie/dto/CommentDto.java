package com.whyranoid.walkie.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.whyranoid.walkie.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @Schema(description = "[POST 요청 필수][GET 응답] 댓글을 단 게시글의 키값", requiredMode = Schema.RequiredMode.REQUIRED, example = "3643")
    private Long postId;

    @Schema(description = "[POST 요청 필수][GET 응답] 댓글 단 유저의 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "1608")
    private Long commenterId;

    @Schema(description = "[POST 요청 필수][GET 응답] 댓글 단 시각", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-09-09 09:09:09")
    private String date;

    @Schema(description = "[POST 요청 필수][GET 응답] 댓글 텍스트(내용)", requiredMode = Schema.RequiredMode.REQUIRED, example = "와 많이 달리셨네요")
    private String content;

    @Schema(description = "[GET 응답] 댓글 키값", example = "352453")
    private Long commentId;

    @Schema(description = "[GET 응답] 댓글 단 유저 정보", implementation = WalkieDto.class)
    private WalkieDto commenter;

    @QueryProjection
    public CommentDto(Comment comment, WalkieDto commenter) {
        this.postId = comment.getPost().getPostId();
        this.commenterId = comment.getUser().getUserId();
        this.date = comment.getDate();
        this.content = comment.getContent();
        this.commentId = comment.getCommentId();
        this.commenter = commenter;
    }
}
