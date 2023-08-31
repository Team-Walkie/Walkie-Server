package com.whyranoid.walkie.repository.querydsl;

import com.whyranoid.walkie.dto.WalkingLikeDto;

public interface WalkingLikeRepositoryCustom {

    Long findWalkingLikeCount(Long walkieId);

    WalkingLikeDto findWalkingLikePeople(Long walkieId);
}
