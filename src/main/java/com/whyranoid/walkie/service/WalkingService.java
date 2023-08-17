package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.History;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.domain.WalkingLike;
import com.whyranoid.walkie.dto.WalkingDto;
import com.whyranoid.walkie.dto.WalkingLikeDto;
import com.whyranoid.walkie.repository.HistoryRepository;
import com.whyranoid.walkie.repository.WalkieRepository;
import com.whyranoid.walkie.repository.WalkingLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service
@RequiredArgsConstructor
public class WalkingService {

    private final WalkieRepository walkieRepository;
    private final HistoryRepository historyRepository;
    private final WalkingLikeRepository walkingLikeRepository;

    public Long startWalking(WalkingDto walkingDto) {
        Walkie user = walkieRepository.findById(walkingDto.getWalkieId()).orElseThrow(EntityNotFoundException::new);
        user.changeStatus('o');
        walkieRepository.save(user);

        Walkie walkie = walkieRepository.findById(walkingDto.getWalkieId()).orElseThrow(EntityNotFoundException::new);

        History input = History.builder()
                .startTime(walkingDto.getStartTime())
                .user(walkie)
                .build();

        History history = historyRepository.save(input);
        return history.getHistoryId();
    }

    public WalkingLikeDto sendWalkingLike(WalkingLikeDto request) {
        Walkie receiver = walkieRepository.findByUserIdAndStatus(request.getReceiverId(), 'o').orElseThrow(EntityNotFoundException::new);

        Walkie sender = walkieRepository.findById(request.getSenderId()).orElseThrow(EntityNotFoundException::new);

        History history = historyRepository.findFirst1ByUser(receiver, Sort.by("startTime").descending()).get(0);

        WalkingLike input = WalkingLike.builder()
                .history(history)
                .liker(sender)
                .build();

        walkingLikeRepository.save(input);
        return request;
    }
}
