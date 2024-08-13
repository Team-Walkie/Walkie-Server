package com.whyranoid.walkie.repository.querydsl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whyranoid.walkie.dto.PostDto;
import com.whyranoid.walkie.dto.QPostDto;
import com.whyranoid.walkie.dto.QWalkieDto;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.core.types.ExpressionUtils.count;
import static com.whyranoid.walkie.domain.QComment.comment;
import static com.whyranoid.walkie.domain.QPost.post;
import static com.whyranoid.walkie.domain.QPostLike.postLike;
import static com.whyranoid.walkie.domain.QWalkie.walkie;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostDto> findCurrentPosts(JPQLQuery<Long> following, Long viewerId, Integer pagingSize, Integer pagingStart) {
        return new ArrayList<>(queryFactory
                .from(post)
                .where(post.user.userId.in(following))
                .orderBy(post.date.desc())
                .offset(pagingStart)
                .limit(pagingSize)
                .leftJoin(postLike).on(postLike.post.postId.eq(post.postId))
                .join(walkie).on(walkie.userId.eq(postLike.liker.userId))
                .transform(groupBy(post.postId).as(new QPostDto(
                        post,
                        Expressions.asNumber(viewerId),
                        list(new QWalkieDto(walkie)),
                        ExpressionUtils.as(
                                JPAExpressions.select(count(comment.commentId))
                                        .from(comment)
                                        .where(comment.post.postId.eq(post.postId)),
                                "commentCount")
                ))).values());
    }

    @Override
    public List<PostDto> findMyPosts(Long viewerId, Integer pagingSize, Integer pagingStart) {
        return new ArrayList<>(queryFactory
                .from(post)
                .where(post.user.userId.eq(viewerId))
                .orderBy(post.date.desc())
                .offset(pagingStart)
                .limit(pagingSize)
                .leftJoin(postLike).on(postLike.post.postId.eq(post.postId))
                .join(walkie).on(walkie.userId.eq(postLike.liker.userId))
                .transform(groupBy(post.postId).as(new QPostDto(
                        post,
                        Expressions.asNumber(viewerId),
                        list(new QWalkieDto(walkie)),
                        ExpressionUtils.as(
                                JPAExpressions.select(count(comment.commentId))
                                        .from(comment)
                                        .where(comment.post.postId.eq(post.postId)),
                                "commentCount")
                ))).values());
    }

    @Override
    public List<PostDto> findEveryPosts(Long viewerId, Integer pagingSize, Integer pagingStart) {
        return new ArrayList<>(queryFactory
                .from(post)
                .orderBy(post.date.desc())
                .offset(pagingStart)
                .limit(pagingSize)
                .leftJoin(postLike).on(postLike.post.postId.eq(post.postId))
                .join(walkie).on(walkie.userId.eq(postLike.liker.userId))
                .transform(groupBy(post.postId).as(new QPostDto(
                        post,
                        Expressions.asNumber(viewerId),
                        list(new QWalkieDto(walkie)),
                        ExpressionUtils.as(
                                JPAExpressions.select(count(comment.commentId))
                                        .from(comment)
                                        .where(comment.post.postId.eq(post.postId)),
                                "commentCount")
                ))).values());
    }

    @Override
    public Long findPostId(String photo, String date) {
        return queryFactory
                .select(post.postId)
                .from(post)
                .where(post.photo.eq(photo).and(post.date.eq(date)))
                .fetchOne();
    }
}
