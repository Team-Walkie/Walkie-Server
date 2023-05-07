package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.dto.ChallengeDto;
import com.whyranoid.walkie.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService{

    private final ChallengeRepository challengeRepository;

    public List<Challenge> getChallenges(Long userId, char challengeStatus) {
        List<Challenge> result = challengeRepository.getChallenges(userId, challengeStatus);
        return result;
    }

    public ChallengeDto getChallengeDetail(Long challengeId, Long userId) {
        return challengeRepository.getChallengeDetail(challengeId, userId);
    }
}
