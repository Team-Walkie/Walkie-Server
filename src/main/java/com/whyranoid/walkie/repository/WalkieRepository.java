package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Walkie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalkieRepository extends JpaRepository<Walkie, Long> {

    Optional<Walkie> findByUserName(String userName);
}
