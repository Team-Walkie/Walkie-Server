package com.whyranoid.walkie.dto;

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

    ChallengeDto challenge;

    List<Walkie> walkies;

    @Builder
    public ChallengeDetailDto(ChallengeDto challenge, List<Walkie> walkies) {
        this.challenge = challenge;
        this.walkies = walkies;
    }
}
