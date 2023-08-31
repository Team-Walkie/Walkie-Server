package com.whyranoid.walkie.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whyranoid.walkie.repository.querydsl.WalkingLikeRepositoryCustom;
import lombok.RequiredArgsConstructor;

import static com.whyranoid.walkie.domain.QHistory.history;
import static com.whyranoid.walkie.domain.QWalkingLike.walkingLike;

@RequiredArgsConstructor
public class WalkingLikeRepositoryImpl implements WalkingLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findWalkingLikeCount(Long walkieId) {
        return queryFactory
                .select(walkingLike)
                .from(walkingLike)
                .where(walkingLike.history.historyId.eq(findCurrentHistory(walkieId))
                        .and(walkingLike.liker.isNotNull()))
                .stream().count();
    }

    public JPQLQuery<Long> findCurrentHistory(Long walkieId) {
        return JPAExpressions
                .select(history.historyId)
                .from(history)
                .where(history.user.userId.eq(walkieId))
                .orderBy(history.historyId.desc())
                .limit(1);
    }
}
