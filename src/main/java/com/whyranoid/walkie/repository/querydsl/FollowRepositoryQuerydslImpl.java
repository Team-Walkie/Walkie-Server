package com.whyranoid.walkie.repository.querydsl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whyranoid.walkie.dto.FollowDto;
import com.whyranoid.walkie.dto.QFollowDto;
import com.whyranoid.walkie.dto.QWalkieDto;
import com.whyranoid.walkie.dto.WalkieDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.whyranoid.walkie.domain.QFollow.follow;
import static com.whyranoid.walkie.domain.QWalkie.walkie;

@RequiredArgsConstructor
public class FollowRepositoryQuerydslImpl implements FollowRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;


    @Override
    public void deleteFollowing(Long followerId, Long followedId) {
        queryFactory
                .delete(follow)
                .where(follow.followed.userId.eq(followedId)
                        .and(follow.follower.userId.eq(followerId)))
                .execute();
    }

    @Override
    public FollowDto findFollowing(Long followerId, Long followedId) {
        return queryFactory
                .select(new QFollowDto(follow, follow.isNull()))
                .from(follow)
                .where(follow.followed.userId.eq(followedId)
                        .and(follow.follower.userId.eq(followerId)))
                .fetchOne();
    }

    @Override
    public List<WalkieDto> findFollowerList(Long whoseId) {
        return findFollowerList(whoseId, false, findFollowerIdList(whoseId));
    }

    @Override
    public List<WalkieDto> findFollowingList(Long whoseId) {
        return  findFollowerList(whoseId, true, findFollowedIdList(whoseId));
    }

    public List<WalkieDto> findFollowerList(Long whoseId, boolean isFollowing, JPQLQuery<Long> followIdList) {
        return queryFactory
                .select(new QWalkieDto(walkie))
                .from(walkie)
                .where(walkie.userId.in(followIdList))
                .fetch();
    }

    public JPQLQuery<Long> findFollowerIdList(Long whoseId) {
        return JPAExpressions
                .select(follow.follower.userId)
                .from(follow)
                .where(follow.followed.userId.eq(whoseId));
    }

    public JPQLQuery<Long> findFollowedIdList(Long whoseId) {
        return JPAExpressions
                .select(follow.followed.userId)
                .from(follow)
                .where(follow.follower.userId.eq(whoseId));
    }
}
