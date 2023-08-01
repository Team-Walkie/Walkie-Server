package com.whyranoid.walkie.repository.querydsl;

import com.whyranoid.walkie.dto.WalkieDto;

import java.util.List;

public interface FollowRepositoryQuerydsl {

        List<WalkieDto> findFollowerList(Long whoseId);

        List<WalkieDto> findFollowingList(Long whoseId);

}
