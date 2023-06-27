package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ChallengeRepository {

    @PersistenceContext
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

    public void insertChallengeStatus(ChallengeStatus cs) {
        em.persist(cs);
    }
    public void updateChallengeStatus(Long statusId, char status) {
        ChallengeStatus cs = em.find(ChallengeStatus.class, statusId);
        cs.setStatus(status);
    }

    public void deleteChallengeStatus(Long statusId) {
        ChallengeStatus cs = em.find(ChallengeStatus.class, statusId);
        em.remove(cs);
    }
}
