package com.whyranoid.walkie.dto;

import com.whyranoid.walkie.domain.Badge;
import com.whyranoid.walkie.domain.Challenge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeDto {
    @Schema(description = "챌린지 키값", example = "2")
    private Long challengeId;

    @Schema(description = "챌린지 카테고리(C, D, L)", example = "L")
    private Character category;

    @Schema(description = "챌린지 제목", example = "햄버거 세트 불태우기")
    private String name;

    @Schema(description = "챌린지 이미지", example = "challenge_img2")
    private String img;

    @Schema(description = "챌린지에 매핑된 뱃지 정보")
    private Badge badge;

    @Schema(description = "챌린지 내용", example = "햄버거 세트의 평균 칼로리는 1110kcal 입니다. 일주일 동안 걷기로 햄버거 세트 태우기 도전!")
    private String content;

    @Schema(description = "유저의 챌린지 도전 상태. 챌린지 시작 전 : ‘N’, 챌린지 진행 중 : ‘P’, 챌린지 완료 ‘C’", nullable = true, example = "P")
    private Character status;

    @Schema(description = "유저의 챌린지 진행도", nullable = true, example = "0")
    private Integer progress;

    @Schema(description = "1회 당 목표 지속시간(분)", nullable = true, example = "20")
    private Integer period;

    @Schema(description = "달성 인정 시간대 시작시각", nullable = true, example = "0600")
    private String startTime;

    @Schema(description = "달성 인정 시간대 종료시각", nullable = true, example = "1200")
    private String endTime;

    @Schema(description = "목표 달성 칼로리", nullable = true, example = "1110")
    private Integer calorie;

    @Schema(description = "목표 달성 거리", nullable = true, example = "1000(m)")
    private Integer distance;

    @Schema(example = "60", nullable = true, description = "목표 달성 시간(분)")
    private Integer time;

    @Schema(example = "10", nullable = true, description = "목표 달성 운동 횟수")
    private Integer goalCount;

    @Schema(example = "1", nullable = true, description = "제한기간(일) (ex.도전 시작 후 7일 이내)")
    private Integer timeLimit;

    @Schema(example = "1", nullable = true, description = "1일 당 인정 가능한 최대 운동 횟수")
    private Integer limitPerDay;

    @Schema(example = "2024-01-01 08:30:00", description = "챌린지 도전 시작 시각")
    private String challengeSdate;

    @Schema(example = "2024-12-31 20:30:00", nullable = true, description = "챌린지 도전 종료 시각")
    private String challengeEdate;

    @Schema(example = "1521.79", description = "챌린지 도전 후 누적 운동 거리(m)")
    private Double accDistance;

    @Schema(example = "01:30:14", description = "챌린지 도전 후 누적 운동 시간")
    private String accTime;

    @Schema(example = "250.5", description = "챌린지 도전 후 누적 소모 칼로리")
    private Double accCalories;

    @Schema(example = "2", description = "챌린지 도전 후 누적 운동 횟수")
    private Integer accCount;

    public ChallengeDto(Challenge challenge) {
        this.challengeId = challenge.getChallengeId();
        this.category = challenge.getCategory();
        this.badge = challenge.getBadge();
        this.content = challenge.getContent();
        this.name = challenge.getName();
        this.img = challenge.getImg();
        this.period = challenge.getPeriod();
        this.startTime = challenge.getStartTime();
        this.endTime = challenge.getEndTime();
        this.calorie = challenge.getCalorie();
        this.distance = challenge.getDistance();
        this.time = challenge.getTime();
        this.goalCount = challenge.getGoalCount();
        this.timeLimit = challenge.getTimeLimit();
        this.limitPerDay = challenge.getLimitPerDay();
    }
}
