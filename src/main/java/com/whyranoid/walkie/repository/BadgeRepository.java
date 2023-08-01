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

    public List<BadgeDto> getBadges(Long userId) {
        return em.createQuery("select new com.whyranoid.walkie.dto.response.BadgeDto(b.badgeId, b.img, b.badgeName, bc.receivedAt) from BadgeCollection bc left join Badge b on bc.badgeId = b.badgeId where bc.userId = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    public void obtainBadge(BadgeCollection bc)
    { em.persist(bc); }
}
