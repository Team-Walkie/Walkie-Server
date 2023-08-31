package com.whyranoid.walkie.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkingDto {

    @Schema(description = "요청 필수 파라미터 - 워키 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Long walkieId;

    @Schema(description = "요청 필수 파라미터 - 운동 시작 시간", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-07-31T15:08:31.689Z")
    private Date startTime;

    @Schema(description = "응답 파라미터 - 생성된 기록의 아이디", example = "15")
    private Long historyId;

    @Schema(description = "요청 파라미터 - 갱신할 사용자 상태", example = "R")
    private Character newStatus;

//    @Schema(description = "(임시) 요청 파라미터 - 인증 아이디|", example = "SUPER-SECRET-KEY")
//    private String authId;

    @Builder
    public WalkingDto(Long walkieId, Date startTime, Long historyId, Character newStatus, String authId, Long likerId) {
        this.walkieId = walkieId;
        this.startTime = startTime;
        this.historyId = historyId;
        this.newStatus = newStatus;
//        this.authId = authId;
    }
}
