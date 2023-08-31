package com.whyranoid.walkie.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HistoryResponse {
    @Schema(description = "운동기록 ID", example = "1")
    private Long historyId;

    @Schema(description = "날짜", example = "2021-10-01")
    private String date;

    @Schema(description = "거리", example = "3.12")
    private Double distance;

    @Schema(description = "시작 시간", example = "논의필요")
    private String startTime;

    @Schema(description = "종료 시간", example = "논의필요")
    private String endTime;

    @Schema(description = "총 시간", example = "논의필요")
    private Integer totalTime;

    @Schema(description = "칼로리", example = "73")
    private Integer calorie;

    @Schema(description = "걸음 수", example = "4251")
    private Integer step;

    @Schema(description = "경로", example = "논의필요")
    private String path;

    @Builder
    HistoryResponse(Long historyId, String date, Double distance, String startTime, String endTime, Integer totalTime, Integer calorie, Integer step, String path) {
        this.historyId = historyId;
        this.date = date;
        this.distance = distance;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.calorie = calorie;
        this.step = step;
        this.path = path;
    }
}
