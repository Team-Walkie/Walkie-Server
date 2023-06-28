package com.whyranoid.walkie.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkingStartRequest {

    @NotNull
    private WalkieStatusChangeRequest walkieStatusChangeRequest;

    // TODO: 운동 시작 정보 추가
    // TODO: 중첩 DTO 해소해서 서비스 내에서 따로 상태변경까지 처리하도록 변경

    @Builder
    public WalkingStartRequest(WalkieStatusChangeRequest walkieStatusChangeRequest) {
        this.walkieStatusChangeRequest = walkieStatusChangeRequest;
    }
}
