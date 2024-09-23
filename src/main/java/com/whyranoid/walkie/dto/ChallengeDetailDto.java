package com.whyranoid.walkie.dto;

import com.whyranoid.walkie.domain.Walkie;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChallengeDetailDto {

    @Schema(description = "[응답] 챌린지 상세 정보")
    ChallengeDto challenge;

    @Schema(description = "[응답] ''본인 제외'' 현재 해당 챌린지에 참가 중인 유저 리스트")
    List<Walkie> walkies;

    @Builder
    public ChallengeDetailDto(ChallengeDto challenge, List<Walkie> walkies) {
        this.challenge = challenge;
        this.walkies = walkies;
    }
}
