package com.whyranoid.walkie.repository.querydsl;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whyranoid.walkie.dto.PostDto;
import com.whyranoid.walkie.dto.QPostDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.whyranoid.walkie.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // TODO: 나의 좋아요 여부 반영
    // TODO: 좋아요 누른 사람 목록 반영
    @Override
    public List<PostDto> findCurrentPosts(JPQLQuery<Long> following, Long viewerId, Integer pagingSize, Integer pagingStart) {
        return queryFactory
                .select(new QPostDto(post, Expressions.asNumber(viewerId)))
                .from(post)
                .where(post.user.userId.in(following))
                .orderBy(post.date.desc())
                .offset(pagingStart)
                .limit(pagingSize)
                .fetch();
    }
}
