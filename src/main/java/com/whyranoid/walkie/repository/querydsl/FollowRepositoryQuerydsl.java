package com.whyranoid.walkie.repository.querydsl;

import com.whyranoid.walkie.dto.FollowDto;
import com.whyranoid.walkie.dto.WalkieDto;

import java.util.List;

public interface FollowRepositoryQuerydsl {

        void deleteFollowing(Long followerId, Long followedId);

        FollowDto findFollowing(Long followerId, Long followedId);

        List<WalkieDto> findFollowerList(Long whoseId);

        List<WalkieDto> findFollowingList(Long whoseId);

}
