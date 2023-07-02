package com.whyranoid.walkie.dto;

import com.whyranoid.walkie.domain.ChallengeStatus;
import com.whyranoid.walkie.domain.Walkie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChallengeDetailDto {
    List<ChallengeStatus> challenges;

    List<Walkie> walkies;

    @Builder
    public ChallengeDetailDto(List<ChallengeStatus> challenges, List<Walkie> walkies) {
        this.challenges = challenges;
        this.walkies = walkies;
    }
}
