package com.whyranoid.walkie.repository.querydsl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whyranoid.walkie.dto.QWalkieDto;
import com.whyranoid.walkie.dto.WalkieDto;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

import static com.whyranoid.walkie.domain.QAgreement.agreement;
import static com.whyranoid.walkie.domain.QBadgeCollection.badgeCollection;
import static com.whyranoid.walkie.domain.QChallengeStatus.challengeStatus;
import static com.whyranoid.walkie.domain.QComment.comment;
import static com.whyranoid.walkie.domain.QFollow.follow;
import static com.whyranoid.walkie.domain.QHistory.history;
import static com.whyranoid.walkie.domain.QPost.post;
import static com.whyranoid.walkie.domain.QPostLike.postLike;
import static com.whyranoid.walkie.domain.QWalkie.walkie;
import static com.whyranoid.walkie.domain.QWalkingLike.walkingLike;

@RequiredArgsConstructor
@Transactional
public class WalkieRepositoryImpl implements WalkieRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WalkieDto> findByUserNameMatched(String keyword) {
        return queryFactory
                .select(new QWalkieDto(walkie))
                .from(walkie)
                .where(walkie.userName.startsWith(keyword))
                .fetch();
    }

    @Override
    public void deleteLeftUser(Long walkieId) {
        // agreement 삭제
        queryFactory
                .delete(agreement)
                .where(agreement.user.userId.eq(walkieId))
                .execute();

        // badge_collection 삭제
        queryFactory
                .delete(badgeCollection)
                .where(badgeCollection.walkieId.eq(walkieId))
                .execute();

        // challenge_status 삭제
        queryFactory
                .delete(challengeStatus)
                .where(challengeStatus.walkie.userId.eq(walkieId))
                .execute();

        // follow 삭제
        queryFactory
                .delete(follow)
                .where(follow.followed.userId.eq(walkieId)
                        .or(follow.follower.userId.eq(walkieId)))
                .execute();

        // comment 삭제
        queryFactory
                .delete(comment)
                .where(comment.user.userId.eq(walkieId)
                        .or(comment.post.postId.in(
                                JPAExpressions
                                        .select(post.postId)
                                        .from(post)
                                        .where(post.user.userId.eq(walkieId))
                        )))
                .execute();

        // post_like 삭제
        queryFactory
                .delete(postLike)
                .where(postLike.liker.userId.eq(walkieId)
                        .or(postLike.post.postId.in(
                                JPAExpressions
                                        .select(post.postId)
                                        .from(post)
                                        .where(post.user.userId.eq(walkieId))
                        )))
                .execute();

        // post 삭제
        queryFactory
                .delete(post)
                .where(post.user.userId.eq(walkieId))
                .execute();

        // walking_like 삭제
        queryFactory
                .delete(walkingLike)
                .where(walkingLike.liker.userId.eq(walkieId)
                        .or(walkingLike.history.historyId.in(
                                JPAExpressions
                                        .select(history.historyId)
                                        .from(history)
                                        .where(history.user.userId.eq(walkieId))
                        )))
                .execute();

        // history 삭제
        queryFactory
                .delete(history)
                .where(history.user.userId.eq(walkieId))
                .execute();
    }
}
