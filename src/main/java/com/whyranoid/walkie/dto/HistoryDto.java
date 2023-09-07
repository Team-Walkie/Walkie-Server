package com.whyranoid.walkie.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryDto {

    @Schema(description = "요청 필수 파라미터 - 워키 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Long walkieId;

    @Schema(description = "요청 필수 파라미터 - 구글 uid", requiredMode = Schema.RequiredMode.REQUIRED, example = "super-secret-key")
    private String authId;

    @Schema(description = "요청 필수 파라미터 - 히스토리 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "57245")
    private Long historyId;

    @Schema(description = "요청 필수 파라미터 - 운동을 끝낸 시각", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-09-09 09:09:09")
    private String endTime;

    @Schema(description = "요청 필수 파라미터 - 운동한 시간(초)", requiredMode = Schema.RequiredMode.REQUIRED, example = "39431")
    private Integer totalTime;

    @Schema(description = "요청 파라미터 - 운동한 거리(미터)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1842.5")
    private Double distance;

    @Schema(description = "요청 파라미터 - 소비 칼로리", requiredMode = Schema.RequiredMode.REQUIRED, example = "300")
    private Integer calorie;

    @Schema(description = "요청 파라미터 - 걸음 수", requiredMode = Schema.RequiredMode.REQUIRED, example = "3094")
    private Integer step;

    @Schema(description = "요청 필수 파라미터 - 달린 경로", requiredMode = Schema.RequiredMode.REQUIRED)
    private String path;

    @Builder
    public HistoryDto(Long walkieId, String authId, Long historyId, String date, String endTime, Integer totalTime, Double distance, Integer calorie, Integer step, String path) {
        this.walkieId = walkieId;
        this.authId = authId;
        this.historyId = historyId;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.distance = distance;
        this.calorie = calorie;
        this.step = step;
        this.path = path;
    }
}
