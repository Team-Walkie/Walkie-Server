package com.whyranoid.walkie.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whyranoid.walkie.dto.QWalkieDto;
import com.whyranoid.walkie.dto.WalkieDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.whyranoid.walkie.domain.QWalkie.walkie;

@RequiredArgsConstructor
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
}
