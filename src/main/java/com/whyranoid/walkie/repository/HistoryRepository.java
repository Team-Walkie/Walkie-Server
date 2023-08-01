package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
