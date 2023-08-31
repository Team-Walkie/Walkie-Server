package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.History;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.domain.WalkingLike;
import com.whyranoid.walkie.repository.querydsl.WalkingLikeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalkingLikeRepository extends JpaRepository<WalkingLike, Long>, WalkingLikeRepositoryCustom {

    List<WalkingLike> findByHistoryAndLiker(History history, Walkie sender);
}
