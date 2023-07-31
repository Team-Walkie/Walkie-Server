package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.BadgeCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
@RequiredArgsConstructor
public class BadgeRepository {
    @PersistenceContext
    private final EntityManager em;

    public void insertBadgeCollection(BadgeCollection badgeCollection) {
        em.persist(badgeCollection);
    }

}
