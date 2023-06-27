package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
import com.whyranoid.walkie.dto.request.ChallengeStatusChangeRequest;
import com.whyranoid.walkie.dto.response.ChallengeStatusChangeResponse;
import com.whyranoid.walkie.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeService{

    private final ChallengeRepository challengeRepository;

    public List<Challenge> getChallenges(Long userId, char challengeStatus) {
        return challengeRepository.getChallenges(userId, challengeStatus);
    }

    public List<ChallengeStatus> getChallengeDetail(Long challengeId, Long userId) {
        return challengeRepository.getDetailChallenge(challengeId, userId);
    }

    public ChallengeStatusChangeResponse updateChallengeStatus(ChallengeStatusChangeRequest challengeStatusChangeRequest) {
        Character requestStatus = challengeStatusChangeRequest.getStatus();
        Long requestStatusId = challengeStatusChangeRequest.getStatusId();
        try {
            // 챌린지 중도 포기해서 삭제하려는 경우
            if(requestStatus == 'N') {
                challengeRepository.deleteChallengeStatus(requestStatusId);
            }
            // 챌린지 조건에 도달해서 완료로 표시해야 하는 경우
            else if(requestStatus == 'C') {
                challengeRepository.updateChallengeStatus(requestStatusId, requestStatus);
            }

            challengeRepository.updateChallengeStatus(requestStatusId, requestStatus);
            return ChallengeStatusChangeResponse.builder()
                    .status(200)
                    .message("성공")
                    .build();
        } catch(Exception e) {
            return ChallengeStatusChangeResponse.builder()
                    .status(400)
                    .message("실패")
                    .build();
        }

    }
}
