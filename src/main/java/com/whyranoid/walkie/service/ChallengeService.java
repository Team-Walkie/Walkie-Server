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
        challengeRepository.updateChallengeStatus(challengeStatusChangeRequest.getStatusId(), challengeStatusChangeRequest.getStatus());
        return ChallengeStatusChangeResponse.builder()
                        .status(200)
                        .message("성공")
                        .build();
    }
}
