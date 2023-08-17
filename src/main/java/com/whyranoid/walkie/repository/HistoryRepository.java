package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.History;
import com.whyranoid.walkie.domain.Walkie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findFirst1ByUser(Walkie user, Sort sort);
}
