package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRepository extends JpaRepository<Agreement, Long> {
}