package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
import com.whyranoid.walkie.domain.Walkie;
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

    public List<Challenge> getNewChallenges(Long userId) {
        return em.createQuery("select c.name, c.challengeId from Challenge c left join ChallengeStatus cs on cs.challenge.challengeId = c.challengeId and cs.walkie.userId = :userId where cs.walkie is null and c.newFlag = 1")
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Challenge> getPopularChallenges() {
        return em.createQuery("select c.name, c.challengeId, count(*) as duplicated_count from Challenge c left join ChallengeStatus cs on c.challengeId = cs.challenge.challengeId group by c.challengeId, c.name order by duplicated_count desc")
                .getResultList();
    }

    public List<Challenge> getChallengesByCategory(Long userId, char category) {
        return em.createQuery("select c.name, c.challengeId from Challenge c left join ChallengeStatus cs on cs.challenge.challengeId = c.challengeId and cs.walkie.userId = :userId where cs.walkie is null and c.category = :category")
                .setParameter("userId", userId)
                .setParameter("category", category)
                .getResultList();
    }

    public List<Challenge> getProgressChallenges(Long userId) {
        return em.createQuery("select c.name, cs.progress, c.challengeId from ChallengeStatus cs left join Challenge c on cs.challenge.challengeId = c.challengeId where cs.walkie.userId = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<ChallengeStatus> getDetailChallenge(Long challengeId, Long userId) {
        return em.createQuery("select c, s.progress, s.status from ChallengeStatus s left join Challenge c on c.challengeId = s.challenge.challengeId where c.challengeId = :challengeId and s.walkie.userId = :userId")
                .setParameter("challengeId", challengeId)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Challenge getChallengeById(Long challengeId) {
        return em.find(Challenge.class, challengeId);
    }

    public Walkie getWalkieById(Long userId) {
        return em.find(Walkie.class, userId);
    }

    public void insertChallengeStatus(ChallengeStatus cs) { em.persist(cs); }

    public void updateChallengeStatus(Long statusId, char status) {
        ChallengeStatus cs = em.find(ChallengeStatus.class, statusId);
        cs.setStatus(status);
    }

    public void deleteChallengeStatus(Long statusId) {
        ChallengeStatus cs = em.find(ChallengeStatus.class, statusId);
        em.remove(cs);
    }
}
