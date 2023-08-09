package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Follow;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.FollowDto;
import com.whyranoid.walkie.dto.WalkieDto;
import com.whyranoid.walkie.repository.FollowRepository;
import com.whyranoid.walkie.repository.WalkieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final WalkieRepository walkieRepository;

    // 팔로우
    public FollowDto doFollow(FollowDto followDto) {
        Long followedId = followDto.getFollowedId();
        Long followerId = followDto.getFollowerId();

        Walkie follower = walkieRepository.findById(followerId).orElseThrow(EntityNotFoundException::new);
        Walkie followed = walkieRepository.findById(followedId).orElseThrow(EntityNotFoundException::new);

        Follow input = Follow.builder()
                        .follower(follower)
                        .followed(followed)
                        .build();

        FollowDto searchRes = searchFollowing(followDto);
        if (searchRes != null) return searchRes;

        followRepository.save(input);

        return searchFollowing(followDto);
    }

    // 언팔로우
    public FollowDto doUnfollow(FollowDto followDto) {
        Long followedId = followDto.getFollowedId();
        Long followerId = followDto.getFollowerId();

        Walkie follower = walkieRepository.findById(followerId).orElseThrow(EntityNotFoundException::new);
        Walkie followed = walkieRepository.findById(followedId).orElseThrow(EntityNotFoundException::new);

        FollowDto searchRes = searchFollowing(followDto);
        if (searchRes == null) return new FollowDto(followerId, followedId, true);

        followRepository.deleteFollowing(followerId, followedId);

        if (searchFollowing(followDto) != null) throw new IllegalStateException();

        searchRes.setUnfollow(true);
        return searchRes;
    }

    // 팔로우 관계 검색
    public FollowDto searchFollowing(FollowDto followDto) {
        return followRepository.findFollowing(followDto.getFollowerId(), followDto.getFollowedId());
    }

    //  내가 팔로우 하는 사람 목록
    @Transactional(readOnly = true)
    public List<WalkieDto> getFollowingList(Long walkieId) {
        walkieRepository.findById(walkieId).orElseThrow(EntityNotFoundException::new);

        return followRepository.findFollowingList(walkieId);
    }

    //  나를 팔로우 하는 사람 목록
    @Transactional(readOnly = true)
    public List<WalkieDto> getFollowerList(Long walkieId) {
        walkieRepository.findById(walkieId).orElseThrow(EntityNotFoundException::new);

        return followRepository.findFollowerList(walkieId);
    }

    // 운동 중인 팔로우 목록
    public List<WalkieDto> getWalkingFollowingList(Long walkieId) {
        walkieRepository.findById(walkieId).orElseThrow(EntityNotFoundException::new);

        return followRepository.findWalkingFollwingList(walkieId);
    }
}
