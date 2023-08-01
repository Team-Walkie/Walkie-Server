package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Follow;
import com.whyranoid.walkie.repository.querydsl.FollowRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryQuerydsl {
}
