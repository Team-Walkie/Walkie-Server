package com.whyranoid.walkie.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChallengePreviewDto {

    @Schema(example = "1")
    private Long challengeId;

    @Schema(example = "C")
    private Character category;

    @Schema(example = "햄버거 세트 불태우기")
    private String name;

    @Schema(example = "N")
    private Character status;

    @Schema(example = "0")
    private Integer progress;

    @Schema(example = "1")
    private Integer newFlag;

    @Schema(example = "20", description = "1회 당 목표 지속시간(분)")
    private Integer period;

    @Schema(example = "0600", description = "달성 인정 시간대 시작시각")
    private String startTime;

    @Schema(example = "1200", description = "달성 인정 시간대 종료시각")
    private String endTime;

    @Schema(example = "585", description = "목표 달성 칼로리")
    private Integer calorie;

    @Schema(example = "1000", description = "목표 달성 거리")
    private Integer distance;

    @Schema(example = "10", description = "목표 달성 운동횟수")
    private Integer goalCount;

    @Schema(example = "1", description = "제한기간(일) (ex.도전 시작 후 7일 이내)")
    private Integer timeLimit;
}
