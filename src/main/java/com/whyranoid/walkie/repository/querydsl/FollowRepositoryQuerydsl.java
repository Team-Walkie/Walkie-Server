package com.whyranoid.walkie.repository.querydsl;

import com.querydsl.jpa.JPQLQuery;
import com.whyranoid.walkie.dto.FollowDto;
import com.whyranoid.walkie.dto.WalkieDto;

import java.util.List;

public interface FollowRepositoryQuerydsl {

        void deleteFollowing(Long followerId, Long followedId);

        FollowDto findFollowing(Long followerId, Long followedId);

        List<WalkieDto> findFollowerList(Long walkieId);

        List<WalkieDto> findFollowingList(Long walkieId);

        List<WalkieDto> findWalkingFollwingList(Long walkieId);

        List<WalkieDto> findFollowerList(Long whoseId, boolean isFollowing, JPQLQuery<Long> followIdList);

        JPQLQuery<Long> findFollowerIdList(Long whoseId);

        JPQLQuery<Long> findFollowedIdList(Long whoseId);

}
