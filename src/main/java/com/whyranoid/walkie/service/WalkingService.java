package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.WalkingDto;
import com.whyranoid.walkie.dto.response.WalkieStatusChangeResponse;
import com.whyranoid.walkie.dto.response.WalkingStartResponse;
import com.whyranoid.walkie.repository.HistoryRepository;
import com.whyranoid.walkie.repository.WalkieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Transactional
@Service
@RequiredArgsConstructor
public class WalkingService {

    private final WalkieRepository walkieRepository;
    private final HistoryRepository historyRepository;

    public Boolean updateStatus(WalkingDto walkingDto) {
        AtomicReference<Boolean> updateSuccess = new AtomicReference<>();
        Optional<Walkie> foundUser = walkieRepository.findByAuthId(walkingDto.getAuthId());

        foundUser.ifPresentOrElse(
                user -> {
                    user.changeStatus(walkingDto.getNewStatus());
                    walkieRepository.save(user);
                    updateSuccess.set(true);
                },
                () -> updateSuccess.set(false)
        );

        return updateSuccess.get();
    }

    public WalkingStartResponse startWalking(WalkingDto walkingDto) {
        Boolean updateSuccess = updateStatus(walkingDto);

        if (updateSuccess) {
            //TODO: 히스토리 등록하고 리턴값 거기에 맞게 수정
            return WalkingStartResponse.builder()
                    .historyId(-1L)
                    .walkieStatusChangeResponse(WalkieStatusChangeResponse.builder().updateSuccess(false).build())
                    .build();
        }
        else return WalkingStartResponse.builder()
                .historyId(-1L)
                .walkieStatusChangeResponse(WalkieStatusChangeResponse.builder().updateSuccess(false).build())
                .build();
    }
}
