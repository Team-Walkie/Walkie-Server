package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.dto.HistoryDto;
import com.whyranoid.walkie.dto.WalkingDto;
import com.whyranoid.walkie.dto.WalkingLikeDto;
import com.whyranoid.walkie.dto.response.WalkieSignUpResponse;
import com.whyranoid.walkie.service.WalkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "WalkingController")
@RequiredArgsConstructor
@RequestMapping("/api/walk")
@RestController
public class WalkingController {

    private final WalkingService walkingService;

    @Operation(summary = "운동 시작 정보 저장")
    @ApiResponse(responseCode = "200", description = "OK -- 생성된 히스토리 아이디 반환")
    @PostMapping("/start")
    public ResponseEntity<Long> startWalking(@RequestBody WalkingDto walkingDto) {
        return ResponseEntity.ok(walkingService.startWalking(walkingDto));
    }

    @Operation(summary = "운동 중인 친구에게 좋아요 보내기")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = WalkingLikeDto.class)),
            description = "성공 시 요청 그대로 반환")
    @PostMapping("/send-like")
    public ResponseEntity<WalkingLikeDto> sendWalkingLike(@RequestBody WalkingLikeDto walkingLikeDto) {
        return ResponseEntity.ok(walkingService.sendWalkingLike(walkingLikeDto));
    }

    @Operation(summary = "운동 중에 받은 좋아요 수 가져오기")
    @ApiResponse(responseCode = "200", description = "성공 시 좋아요 수 리턴, 실패 시 Exception")
    @GetMapping("/count-like")
    public ResponseEntity<Long> countWalkingLike(@RequestParam Long walkieId, @RequestParam String authId) {
        return ResponseEntity.ok(walkingService.countWalkingLike(walkieId, authId));
    }

    @Operation(summary = "운동 종료 시 받은 좋아요 수와 좋아요 누른 사람 프로필 가져오기")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = WalkingLikeDto.class)),
            description = "성공 시 운동한 사람의 아이디, 받은 좋아요 수, 좋아요 누른 사람의 유저 정보 리스트를 반환")
    @GetMapping("/count-total")
    public ResponseEntity<WalkingLikeDto> getTotalWalkingLike(@RequestParam Long walkieId, @RequestParam String authId) {
        return ResponseEntity.ok(walkingService.getTotalWalkingLike(walkieId, authId));
    }

    @Operation(summary = "운동 종료 시 데이터 저장하기")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = WalkieSignUpResponse.class)),
            description = "저장 성공 시 기록의 DB pk값, 실패 시 -1")
    @PostMapping("/save")
    public ResponseEntity<Long> saveWalkingHistory(@RequestBody HistoryDto historyDto) {
        return ResponseEntity.ok(walkingService.saveWalkingHistory(historyDto));
    }
}
