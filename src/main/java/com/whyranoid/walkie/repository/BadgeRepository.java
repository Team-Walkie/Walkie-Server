package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.BadgeCollection;
import com.whyranoid.walkie.dto.response.BadgeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class BadgeRepository {
    @PersistenceContext
    private final EntityManager em;

    public List<BadgeDto> getBadges(Long walkieId) {
        return em.createQuery("select new com.whyranoid.walkie.dto.response.BadgeDto(b.badgeId, b.img, b.badgeName, bc.receivedAt, bc.isRep, bc.repPosition, bc.walkieId) from BadgeCollection bc left join Badge b on bc.badgeId = b.badgeId where bc.walkieId = :walkieId")
                .setParameter("walkieId", walkieId)
                .getResultList();
    }

    public void obtainBadge(BadgeCollection bc)
    { em.persist(bc); }

    public void updateRepBadges(Long walkieId, List<Long> repBadgeIdList) {
        List<BadgeCollection> repBadgeList = em.createQuery("FROM BadgeCollection WHERE walkieId = :walkieId and badgeId in (:repBadgeIdList)")
                .setParameter("walkieId", walkieId)
                .setParameter("repBadgeIdList", repBadgeIdList)
                .getResultList();

        for(BadgeCollection bc : repBadgeList) {
            bc.setIsRep(true);
            bc.setRepPosition(repBadgeIdList.indexOf(bc.getBadgeId()));
        }

        em.createQuery("UPDATE BadgeCollection SET isRep = false, repPosition = :oNull WHERE walkieId = :walkieId AND badgeId NOT IN (:repBadgeIdList)")
                .setParameter("walkieId", walkieId)
                .setParameter("oNull", null)
                .setParameter("repBadgeIdList", repBadgeIdList)
                .executeUpdate();

        em.flush();
    }
}
