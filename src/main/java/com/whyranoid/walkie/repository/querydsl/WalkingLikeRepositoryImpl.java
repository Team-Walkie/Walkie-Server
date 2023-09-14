package com.whyranoid.walkie.repository.querydsl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whyranoid.walkie.dto.HistoryDto;
import com.whyranoid.walkie.dto.QWalkieDto;
import com.whyranoid.walkie.dto.WalkieDto;
import com.whyranoid.walkie.dto.WalkingLikeDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.whyranoid.walkie.domain.QHistory.history;
import static com.whyranoid.walkie.domain.QWalkie.walkie;
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

    @Override
    public WalkingLikeDto findWalkingLikePeople(Long walkieId) {
        return WalkingLikeDto.builder()
                .receiverId(walkieId)
                .likerCount(findWalkingLikeCount(walkieId))
                .likerProfiles(findLikerList(walkieId))
                .build();
    }

    @Override
    public Long updateCurrentWalkingHistory(HistoryDto historyDto) {
        Long updatedHistory = queryFactory.update(history)
                .set(history.distance, historyDto.getDistance())
                .set(history.endTime, historyDto.getEndTime())
                .set(history.totalTime, historyDto.getTotalTime())
                .set(history.calorie, historyDto.getCalorie())
                .set(history.step, historyDto.getStep())
                .where(history.historyId.eq(historyDto.getHistoryId()))
                .execute();

        Long updatedWalkie = queryFactory.update(walkie)
                .set(walkie.status, 'N')
                .where(walkie.userId.eq(historyDto.getWalkieId()))
                .execute();

        if (updatedHistory == 1 && updatedWalkie == 1) return historyDto.getHistoryId();
        return -1L;
    }

    public JPQLQuery<Long> findCurrentHistory(Long walkieId) {
        return JPAExpressions
                .select(history.historyId)
                .from(history)
                .where(history.user.userId.eq(walkieId))
                .orderBy(history.historyId.desc())
                .limit(1);
    }

    public List<WalkieDto> findLikerList(Long walkerId) {
        return queryFactory
                .select(new QWalkieDto(walkingLike.liker))
                .from(walkingLike)
                .where(walkingLike.history.historyId.eq(findCurrentHistory(walkerId)))
                .fetch();
    }
}
