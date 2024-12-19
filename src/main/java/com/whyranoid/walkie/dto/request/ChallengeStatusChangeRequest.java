package com.whyranoid.walkie.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChallengeStatusChangeRequest {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Long walkieId;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Long challengeId;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "P")
    private Character status;
    @Schema(example = "25", description = "업데이트된 진행률(사용X)")
    private Integer progress;
    @Schema(example = "2024-12-31 20:35:00", description = "챌린지 종료 시 종료시각")
    private String challengeEdate;
    @Schema(example = "540.25", description = "추가할 운동 거리(m)")
    private Double accDistance;
    @Schema(example = "00:35:12", description = "추가할 운동 시간")
    private String accTime;
    @Schema(example = "135.3", description = "추가할 소모 칼로리")
    private Double accCalories;
    @Schema(example = "1", description = "추가할 운동 횟수 (항상 1일 것 같긴 하네요..)")
    private Integer accCount;

    @Builder
    public ChallengeStatusChangeRequest(Long walkieId, Long challengeId, Character status, Integer progress, String challengeEdate, Double accDistance, String accTime, Double accCalories, Integer accCount) {
        this.walkieId = walkieId;
        this.challengeId = challengeId;
        this.status = status;
        this.progress = progress;
        this.challengeEdate = challengeEdate;
        this.accDistance = accDistance;
        this.accTime = accTime;
        this.accCalories = accCalories;
        this.accCount = accCount;
    }
}
