package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChallengeRepository {

    private final EntityManager em;

    public List<Challenge> getChallenges(Long userId, char challengeStatus) {
        return em.createQuery("select c, s.status, s.progress from Challenge c left join ChallengeStatus s " +
                        "on c.challengeId=s.challenge.challengeId where s.walkie.userId = :userId and s.status = :challengeStatus")
                .setParameter("userId", userId)
                .setParameter("challengeStatus", challengeStatus)
                .getResultList();
    }

    public List<ChallengeStatus> getDetailChallenge(Long challengeId, Long userId) {
        return em.createQuery("select c, s.progress, s.status from ChallengeStatus s left join Challenge c on c.challengeId = s.challenge.challengeId where c.challengeId = :challengeId and s.walkie.userId = :userId")
                .setParameter("challengeId", challengeId)
                .setParameter("userId", userId)
                .getResultList();
    }
}
