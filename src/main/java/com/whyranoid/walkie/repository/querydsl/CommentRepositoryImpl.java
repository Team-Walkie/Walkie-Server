package com.whyranoid.walkie.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whyranoid.walkie.dto.CommentDto;
import com.whyranoid.walkie.dto.QCommentDto;
import com.whyranoid.walkie.dto.QWalkieDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.whyranoid.walkie.domain.QComment.comment;
import static com.whyranoid.walkie.domain.QWalkie.walkie;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentDto> findByPostId(Long postId) {
        return queryFactory
                .select(new QCommentDto(comment, new QWalkieDto(walkie)))
                .from(comment)
                .leftJoin(comment.user, walkie).on(walkie.userId.eq(comment.user.userId))
                .where(comment.post.postId.eq(postId))
                .orderBy(comment.date.desc())   // TODO: 댓글 정렬에 대해 상의
                .fetch();
    }
}
