package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.WalkingLike;
import com.whyranoid.walkie.repository.querydsl.WalkingLikeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkingLikeRepository extends JpaRepository<WalkingLike, Long>, WalkingLikeRepositoryCustom {
}
