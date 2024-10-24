package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.response.ChallengePreviewDto;
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

    public List<ChallengePreviewDto> getNewChallenges(Long walkieId) {
        return em.createQuery(
                """
                select new com.whyranoid.walkie.dto.response.ChallengePreviewDto(
                    c.challengeId, 
                    c.category, 
                    c.name, 
                    cs.status, 
                    cs.progress, 
                    c.newFlag,
                    c.period,
                    c.startTime,
                    c.endTime,
                    c.calorie,
                    c.distance,
                    c.goalCount,
                    c.timeLimit
                ) 
                from Challenge c left join ChallengeStatus cs 
                    on cs.challenge.challengeId = c.challengeId 
                    and cs.walkie.userId = :walkieId 
                where cs.walkie is null 
                    and c.newFlag = 1
                """
                )
                .setParameter("walkieId", walkieId)
                .getResultList();
    }

    public List<ChallengePreviewDto> getPopularChallenges() {
        return em.createQuery(
                """
                select new com.whyranoid.walkie.dto.response.ChallengePreviewDto(
                    c.challengeId, 
                    c.category, 
                    c.name, 
                    cs.status, 
                    cs.progress, 
                    c.newFlag,
                    c.period,
                    c.startTime,
                    c.endTime,
                    c.calorie,
                    c.distance,
                    c.goalCount,
                    c.timeLimit
                )
                from Challenge c left join ChallengeStatus cs 
                    on c.challengeId = cs.challenge.challengeId 
                group by c.challengeId, c.name 
                order by count(*) desc
                """
                )
                .getResultList();
    }

    public List<ChallengePreviewDto> getChallengesByCategory(Long walkieId, char category) {
        return em.createQuery(
                """
                select  new com.whyranoid.walkie.dto.response.ChallengePreviewDto(
                    c.challengeId, 
                    c.category, 
                    c.name, 
                    cs.status, 
                    cs.progress, 
                    c.newFlag
                ) 
                from Challenge c left join ChallengeStatus cs 
                    on cs.challenge.challengeId = c.challengeId 
                    and cs.walkie.userId = :walkieId 
                where cs.walkie is null 
                    and c.category = :category
                """
                )
                .setParameter("walkieId", walkieId)
                .setParameter("category", category)
                .getResultList();
    }

    public List<ChallengePreviewDto> getProgressChallenges(Long walkieId) {
        return em.createQuery(
                """
                select new com.whyranoid.walkie.dto.response.ChallengePreviewDto(
                    c.challengeId, 
                    c.category, 
                    c.name, 
                    cs.status, 
                    cs.progress, 
                    c.newFlag,
                    c.period,
                    c.startTime,
                    c.endTime,
                    c.calorie,
                    c.distance,
                    c.goalCount,
                    c.timeLimit
                ) 
                from ChallengeStatus cs left join Challenge c 
                    on cs.challenge.challengeId = c.challengeId 
                where cs.walkie.userId = :walkieId
                """
                )
                .setParameter("walkieId", walkieId)
                .getResultList();
    }

    public Object getChallengeDetail(Long challengeId, Long walkieId) {
        return em.createQuery(
                    """
                    select new com.whyranoid.walkie.dto.ChallengeDto(
                        c.challengeId,
                        c.category,
                        c.startTime,
                        c.endTime,
                        c.calorie,
                        c.distance,
                        c.badge,
                        c.content,
                        c.name,
                        c.img,
                        c.period,
                        cs.status,
                        cs.progress
                    )
                    from Challenge c left join ChallengeStatus cs
                    on c.challengeId = cs.challenge.challengeId and cs.walkie.userId = :walkieId
                    where c.challengeId = :challengeId
                    """
                )
                .setParameter("challengeId", challengeId)
                .setParameter("walkieId", walkieId)
                .getSingleResult();
    }

    public List<Walkie> getChallengeMember(Long challengeId, Long walkieId) {
        return em.createQuery(
                """
                select walkie 
                from ChallengeStatus cs left join Walkie walkie 
                    on cs.walkie.userId = walkie.userId 
                where cs.challenge.challengeId = :challengeId 
                    and cs.walkie.userId != :walkieId
                """
                )
                .setParameter("challengeId", challengeId)
                .setParameter("walkieId", walkieId)
                .getResultList();
    }

    public Challenge getChallengeById(Long challengeId) {
        return em.find(Challenge.class, challengeId);
    }

    public Walkie getWalkieById(Long walkieId) {
        return em.find(Walkie.class, walkieId);
    }

    public void insertChallengeStatus(ChallengeStatus cs) { em.persist(cs); }

    public void updateChallengeStatus(Long walkieId, Long challengeId, char status) {
        ChallengeStatus cs = getChallengeStatus(walkieId, challengeId);
        cs.setStatus(status);
    }

    public void deleteChallengeStatus(Long walkieId, Long challengeId) {
        ChallengeStatus cs = getChallengeStatus(walkieId, challengeId);
        em.remove(cs);
        em.flush();
    }

    public ChallengeStatus getChallengeStatus(Long walkieId, Long challengeId) {
        List<ChallengeStatus> cs = em.createQuery(
                """
                FROM ChallengeStatus 
                where walkie_id = :walkieId 
                    and challenge.challengeId = :challengeId
                """
                )
                .setParameter("walkieId", walkieId)
                .setParameter("challengeId", challengeId)
                .getResultList();

        return cs.get(0);
    }
}
