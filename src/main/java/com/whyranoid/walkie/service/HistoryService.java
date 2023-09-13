package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.response.HistoryResponse;
import com.whyranoid.walkie.repository.HistoryRepository;
import com.whyranoid.walkie.repository.WalkieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final WalkieRepository walkieRepository;

    public List<HistoryResponse> getHistory(Long walkieId) {
        Walkie walkie = walkieRepository.findByUserId(walkieId).orElseThrow();
        ArrayList<HistoryResponse> result = new ArrayList<>();
        historyRepository.findAllByUser(walkie).forEach(
                history -> result.add(HistoryResponse.builder()
                        .historyId(history.getHistoryId())
                        .date(history.getDate())
                        .distance(history.getDistance())
                        .startTime(history.getStartTime())
                        .endTime(history.getEndTime())
                        .totalTime(history.getTotalTime())
                        .calorie(history.getCalorie())
                        .step(history.getStep())
                        .build()
        ));
        return result.stream().toList();
    }
}