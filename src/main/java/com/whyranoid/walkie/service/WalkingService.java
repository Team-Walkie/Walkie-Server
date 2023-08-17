package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.History;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.WalkingDto;
import com.whyranoid.walkie.dto.response.WalkieStatusChangeResponse;
import com.whyranoid.walkie.dto.response.WalkingStartResponse;
import com.whyranoid.walkie.repository.HistoryRepository;
import com.whyranoid.walkie.repository.WalkieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service
@RequiredArgsConstructor
public class WalkingService {

    private final WalkieRepository walkieRepository;
    private final HistoryRepository historyRepository;

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
}
