package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
import com.whyranoid.walkie.repository.ChallengeRepository;
import com.whyranoid.walkie.repository.ChallengeRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService{

    private final ChallengeRepositoryImpl challengeRepositoryImpl;
    private final ChallengeRepository challengeRepository;

    public List<Challenge> getChallenges(Long userId, char challengeStatus) {
        return challengeRepositoryImpl.getChallenges(userId, challengeStatus);
    }

    public ChallengeStatus getChallengeDetail(Long challengeId, Long userId) {
        return challengeRepository.findChallengeStatusByChallenge(challengeId, userId);
    }
}
