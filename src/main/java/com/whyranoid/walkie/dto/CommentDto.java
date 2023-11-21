package com.whyranoid.walkie.dto;

import com.whyranoid.walkie.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @Schema(description = "[요청 필수] 댓글을 단 게시글의 키값", requiredMode = Schema.RequiredMode.REQUIRED, example = "3643")
    private Long postId;

    @Schema(description = "[요청 필수] 댓글 단 유저의 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "1608")
    private Long commenter;

    @Schema(description = "[요청 필수] 댓글 단 시각", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-09-09 09:09:09")
    private String date;

    @Schema(description = "[요청 필수] 댓글 텍스트(내용)", requiredMode = Schema.RequiredMode.REQUIRED, example = "와 많이 달리셨네요")
    private String content;

    public CommentDto(Comment comment) {
        this.postId = comment.getPost().getPostId();
        this.commenter = comment.getUser().getUserId();
        this.date = comment.getDate();
        this.content = comment.getContent();
    }
}
