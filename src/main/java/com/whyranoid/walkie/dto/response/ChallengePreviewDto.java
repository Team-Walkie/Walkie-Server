package com.whyranoid.walkie.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChallengePreviewDto {

    @Schema(example = "1", description = "챌린지 키값")
    private Long challengeId;

    @Schema(example = "C", description = "챌린지 카테고리(C, D, L)")
    private Character category;

    @Schema(example = "햄버거 세트 불태우기", description = "챌린지 제목")
    private String name;

    @Schema(example = "N", nullable = true, description = "유저의 챌린지 도전 상태. 챌린지 시작 전 : ‘N’, 챌린지 진행 중 : ‘P’, 챌린지 완료 ‘C’")
    private Character status;

    @Schema(description = "유저의 챌린지 진행도", nullable = true, example = "0")
    private Integer progress;

    @Schema(example = "1")
    private Integer newFlag;

    @Schema(example = "20", nullable = true, description = "1회 당 목표 지속시간(분)")
    private Integer period;

    @Schema(example = "0600", nullable = true, description = "달성 인정 시간대 시작시각")
    private String startTime;

    @Schema(example = "1200", nullable = true, description = "달성 인정 시간대 종료시각")
    private String endTime;

    @Schema(example = "585", nullable = true, description = "목표 달성 칼로리")
    private Integer calorie;

    @Schema(example = "1000", nullable = true, description = "목표 달성 거리")
    private Integer distance;

    @Schema(example = "60", nullable = true, description = "목표 달성 시간(분)")
    private Integer time;

    @Schema(example = "10", nullable = true, description = "목표 달성 운동횟수")
    private Integer goalCount;

    @Schema(example = "1", description = "제한기간(일) (ex.도전 시작 후 7일 이내)")
    private Integer timeLimit;

    @Schema(example = "1", nullable = true, description = "1일 당 인정 가능한 최대 운동 횟수")
    private Integer limitPerDay;
}
