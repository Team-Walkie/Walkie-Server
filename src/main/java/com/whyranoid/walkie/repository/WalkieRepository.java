package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.repository.querydsl.WalkieRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalkieRepository extends JpaRepository<Walkie, Long>, WalkieRepositoryCustom {

    Optional<Walkie> findByUserName(String userName);

    Optional<Walkie> findByAuthId(String authId);

    Optional<Walkie> findByUserId(Long walkieId);

    Optional<Walkie> findByUserIdAndStatus(Long id, Character status);
}
