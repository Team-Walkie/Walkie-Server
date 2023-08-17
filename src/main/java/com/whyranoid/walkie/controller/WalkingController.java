package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.dto.WalkingDto;
import com.whyranoid.walkie.dto.WalkingLikeDto;
import com.whyranoid.walkie.service.WalkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "WalkingController")
@RequiredArgsConstructor
@RequestMapping("/api/walk")
@RestController
public class WalkingController {

    private final WalkingService walkingService;

    @Operation(description = "운동 시작 정보 저장")
    @PostMapping("/start")
    @ApiResponse(responseCode = "200", description = "OK -- 생성된 히스토리 아이디")
    public ResponseEntity<Long> startWalking(@RequestBody WalkingDto walkingDto) {
        return ResponseEntity.ok(walkingService.startWalking(walkingDto));
    }

    @Operation(description = "운동 중인 친구에게 좋아요 보내기")
    @PostMapping("/send-like")
    public ResponseEntity<WalkingLikeDto> sendWalkingLike(@RequestBody WalkingLikeDto walkingLikeDto) {
        return ResponseEntity.ok(walkingService.sendWalkingLike(walkingLikeDto));
    }

}
