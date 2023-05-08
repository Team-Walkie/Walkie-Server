package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.ChallengeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeStatus, Long> {
        @Query("select s, c from ChallengeStatus s left join Challenge c on c.challengeId = s.challenge.challengeId where c.challengeId = :challengeId and s.walkie.userId = :userId")
        ChallengeStatus findChallengeStatusByChallenge(@Param("challengeId")Long challengeId, @Param("userId")Long userId);
}
