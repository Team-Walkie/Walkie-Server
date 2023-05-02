package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChallengeRepository {

    private final EntityManager em;

    public List<Challenge> getChallenges(Long userId) {
        List<Challenge> challenges = em.createQuery("select c from Challenge c join ChallengeStatus s on c.challengeId=s.challenge.challengeId where s.walkie.userId = :userId")
                .setParameter("userId", userId)
                .getResultList();

        return challenges;
    }
}
