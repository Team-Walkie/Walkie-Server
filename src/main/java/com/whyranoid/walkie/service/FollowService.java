package com.whyranoid.walkie.service;

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

    //  내가 팔로우 하는 사람 목록
    @Transactional(readOnly = true)
    public List<WalkieDto> getFollowingList(Long walkieId) {

        walkieRepository.findById(walkieId).orElseThrow(() -> new EntityNotFoundException());

        return followRepository.findFollowingList(walkieId);
    }

    //  나를 팔로우 하는 사람 목록
    @Transactional(readOnly = true)
    public List<WalkieDto> getFollowerList(Long walkieId) {

        walkieRepository.findById(walkieId).orElseThrow(() -> new EntityNotFoundException());

        return followRepository.findFollowerList(walkieId);
    }
}
