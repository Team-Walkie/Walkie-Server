package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Badge;
import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.request.ChallengeStatusChangeRequest;
import com.whyranoid.walkie.dto.response.ChallengePreviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
                    c.time,
                    c.goalCount,
                    c.timeLimit,
                    c.limitPerDay
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
                    c.time,
                    c.goalCount,
                    c.timeLimit,
                    c.limitPerDay
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
                    c.newFlag,
                    c.period,
                    c.startTime,
                    c.endTime,
                    c.calorie,
                    c.distance,
                    c.time,
                    c.goalCount,
                    c.timeLimit,
                    c.limitPerDay
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
                    c.time,
                    c.goalCount,
                    c.timeLimit,
                    c.limitPerDay
                ) 
                from ChallengeStatus cs left join Challenge c 
                    on cs.challenge.challengeId = c.challengeId 
                where cs.walkie.userId = :walkieId
                  and cs.status = 'P'
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
                        c.name,
                        c.img,
                        c.badge,
                        c.content,
                        cs.status,
                        cs.progress,
                        c.period,
                        c.startTime,
                        c.endTime,
                        c.calorie,
                        c.distance,
                        c.time,
                        c.goalCount,
                        c.timeLimit,
                        c.limitPerDay,
                        cs.challengeSdate,
                        cs.challengeEdate,
                        cs.accDistance,
                        cs.accTime,
                        cs.accCalories,
                        cs.accCount
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

    public void updateChallengeStatus(Long walkieId, Long challengeId, ChallengeStatusChangeRequest request) throws ParseException {
        ChallengeStatus cs = getChallengeStatus(walkieId, challengeId);
        if (request.getStatus() != null) cs.setStatus(request.getStatus());
        if (request.getProgress() != null) cs.setProgress(request.getProgress());
        if (request.getChallengeEdate() != null) cs.setChallengeEdate(request.getChallengeEdate());
        if (request.getAccDistance() != null) {
            Double prev = Optional.ofNullable(cs.getAccDistance()).orElse(0.0);
            cs.setAccDistance(prev + request.getAccDistance());
        }
        if (request.getAccTime() != null) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date prev = format.parse(Optional.ofNullable(cs.getAccTime()).orElse("00:00:00"));
            Date plus = format.parse(request.getAccTime());
            Calendar prevCalendar = Calendar.getInstance();
            Calendar plusCalendar = Calendar.getInstance();
            prevCalendar.setTime(prev);
            plusCalendar.setTime(plus);
            prevCalendar.add(Calendar.HOUR_OF_DAY, plusCalendar.get(Calendar.HOUR_OF_DAY));
            prevCalendar.add(Calendar.MINUTE, plusCalendar.get(Calendar.MINUTE));
            prevCalendar.add(Calendar.SECOND, plusCalendar.get(Calendar.SECOND));

            cs.setAccTime(format.format(prevCalendar.getTime()));
        }
        if (request.getAccCalories() != null) {
            Double prev = Optional.ofNullable(cs.getAccCalories()).orElse(0.0);
            cs.setAccCalories(prev + request.getAccCalories());
        }
        if(request.getAccCount() != null) {
            Integer prev = Optional.ofNullable(cs.getAccCount()).orElse(0);
            cs.setAccCount(prev + request.getAccCount());
        }

        em.flush();
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

    public Badge getObtainedBadge(Long challengeId) {
        return em.find(Challenge.class, challengeId).getBadge();
    }
}
