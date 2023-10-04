package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.History;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.domain.WalkingLike;
import com.whyranoid.walkie.dto.HistoryDto;
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
import java.security.InvalidParameterException;

@Transactional
@Service
@RequiredArgsConstructor
public class WalkingService {

    private final WalkieRepository walkieRepository;
    private final HistoryRepository historyRepository;
    private final WalkingLikeRepository walkingLikeRepository;

    public Long startWalking(WalkingDto walkingDto) {
        Walkie user = walkieRepository.findById(walkingDto.getWalkieId()).orElseThrow(EntityNotFoundException::new);
        user.changeStatus('W');
        walkieRepository.save(user);

        Walkie walkie = walkieRepository.findById(walkingDto.getWalkieId()).orElseThrow(EntityNotFoundException::new);

        History input = History.builder()
                //.date(walkingDto.getStartTime())  // TODO 날짜 형식 반영
                .startTime(walkingDto.getStartTime())    // TODO 날짜 형식 반영
                .user(walkie)
                .build();

        History history = historyRepository.save(input);
        return history.getHistoryId();
    }

    public WalkingLikeDto sendWalkingLike(WalkingLikeDto request) {
        Walkie receiver = walkieRepository.findByUserIdAndStatus(request.getReceiverId(), 'o').orElseThrow(EntityNotFoundException::new);

        Walkie sender = walkieRepository.findById(request.getSenderId()).orElseThrow(EntityNotFoundException::new);

        History history = historyRepository.findFirst1ByUser(receiver, Sort.by("startTime").descending()).get(0);

        boolean already = !walkingLikeRepository.findByHistoryAndLiker(history, sender).isEmpty();
        if (already) return request;

        WalkingLike input = WalkingLike.builder()
                .history(history)
                .liker(sender)
                .build();

        walkingLikeRepository.save(input);
        return request;
    }

    public Long countWalkingLike(Long walkieId, String authId) {
        Walkie authWalkie = walkieRepository.findByAuthId(authId).orElseThrow(EntityNotFoundException::new);

        if (!authWalkie.getUserId().equals(walkieId)) throw new InvalidParameterException();

        return walkingLikeRepository.findWalkingLikeCount(authWalkie.getUserId());
    }

    public WalkingLikeDto getTotalWalkingLike(Long walkieId, String authId) {
        Walkie authWalkie = walkieRepository.findByAuthId(authId).orElseThrow(EntityNotFoundException::new);

        if (!authWalkie.getUserId().equals(walkieId)) throw new InvalidParameterException();

        return walkingLikeRepository.findWalkingLikePeople(walkieId);
    }

    public Long saveWalkingHistory(HistoryDto historyDto) {
        Walkie walkie = walkieRepository.findById(historyDto.getWalkieId()).orElseThrow(EntityNotFoundException::new);

        return walkingLikeRepository.updateCurrentWalkingHistory(historyDto);

    }
}
