package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.dto.ChallengeDto;
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

    public ChallengeDto getChallengeDetail(Long challengeId, Long userId) {
        ChallengeDto result = em.createQuery("select s from Challenge c left join ChallengeStatus s " +
                        "on c.challengeId = s.challenge.challengeId where c.challengeId = :challengeId and s.walkie.userId = :userId", ChallengeDto.class)
                .setParameter("challengeId", challengeId)
                .setParameter("userId", userId)
                .getSingleResult();
        return result;
    }
}
