package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
import com.whyranoid.walkie.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService{

    private final ChallengeRepository challengeRepository;

    public List<Challenge> getChallenges(Long userId, char challengeStatus) {
        return challengeRepository.getChallenges(userId, challengeStatus);
    }

    public List<ChallengeStatus> getChallengeDetail(Long challengeId, Long userId) {
        return challengeRepository.getDetailChallenge(challengeId, userId);
    }
}
