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
        return em.createQuery("select new com.whyranoid.walkie.dto.response.BadgeDto(b.badgeId, b.img, b.badgeName, bc.receivedAt, bc.isRep, bc.walkieId) from BadgeCollection bc left join Badge b on bc.badgeId = b.badgeId where bc.walkieId = :walkieId order by bc.position, bc.collectionId")
                .setParameter("walkieId", walkieId)
                .getResultList();
    }

    public void obtainBadge(BadgeCollection bc)
    { em.persist(bc); }

    public void updateBadgeIndices(Long walkieId, List<Long> badgeIdList) {
        List<BadgeCollection> preBadgeList = em.createQuery("SELECT * FROM BadgeCollection WHERE walkieId = :walkieId")
                .setParameter("walkieId", walkieId)
                .getResultList();

        for(BadgeCollection bc : preBadgeList) {
            int newIdx = badgeIdList.indexOf(bc.getBadgeId());
            bc.setIsRep(newIdx < 5);
            bc.setPosition(newIdx);
        }

        em.flush();
    }

    public List<Long> getBadgeIds(Long walkieId) {
        return em.createQuery("SELECT badgeId FROM BadgeCollection WHERE walkieId = :walkieId")
                .setParameter("walkieId", walkieId)
                .getResultList();
    }
}
