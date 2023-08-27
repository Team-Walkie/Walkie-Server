package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
@RequiredArgsConstructor
public class CommunityRepository {

    @PersistenceContext
    private final EntityManager em;

    public void uploadPost(Post post) {
        em.persist(post);
    }
}
