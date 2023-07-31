package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.dto.ChallengeDetailDto;
import com.whyranoid.walkie.dto.request.ChallengeStatusChangeRequest;
import com.whyranoid.walkie.dto.request.ChallengeStatusCreateRequest;
import com.whyranoid.walkie.dto.response.ChallengePreviewDto;
import com.whyranoid.walkie.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Challenge", description = "챌린지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge")
public class ChallengeController {
    HttpHeaders httpHeaders = new HttpHeaders();
    private final ChallengeService challengeService;

    @Operation(summary = "새로운 챌린지 가져오기", description = "새로운 챌린지들을 가져옵니다.")
    @Parameters({
            @Parameter(name = "userId", description = "유저 아이디", example = "123")
    })
    @ApiResponse(responseCode = "200", description = "호출 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChallengePreviewDto.class))))
    @GetMapping("/challenges/new")
    public ResponseEntity getNewChallenges(@RequestParam("userId") Long userId) {
        List<ChallengePreviewDto> challenges = challengeService.getNewChallenges(userId);
        return new ResponseEntity<>(challenges, httpHeaders, HttpStatus.OK);
    }

    @Operation(summary = "인기 챌린지 가져오기", description = "인기가 많은 순서대로 챌린지들을 가져옵니다.")
    @ApiResponse(responseCode = "200", description = "호출 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChallengePreviewDto.class))))
    @GetMapping("/challenges/top-rank")
    public ResponseEntity getPopularChallenges() {
        List<ChallengePreviewDto> challenges = challengeService.getPopularChallenges();
        return new ResponseEntity<>(challenges, httpHeaders, HttpStatus.OK);
    }

    @Operation(summary = "카테고리별 챌린지 가져오기", description = "카테고리를 넣어주면 해당 카테고리의 챌린지들을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "호출 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChallengePreviewDto.class))))
    @Parameters({
            @Parameter(name = "userId", description = "유저 아이디", example = "123"),
            @Parameter(name = "category", description = "카테고리(L은 라이프, C는 칼로리, D는 거리)", example = "C")
    })
    @GetMapping("/challenges/category")
    public ResponseEntity getChallengesByCategory(@RequestParam("userId") Long userId, @RequestParam("category") char category) {
        List<ChallengePreviewDto> challenges = challengeService.getChallengesByCategory(userId, category);
        return new ResponseEntity<>(challenges, httpHeaders, HttpStatus.OK);
    }

    @Operation(summary = "진행중인 챌린지 가져오기", description = "현재 유저가 진행 중인 챌린지들을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "호출 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChallengePreviewDto.class))))
    @Parameters({
            @Parameter(name = "userId", description = "유저 아이디", example = "123")
    })
    @GetMapping("/challenges/progress")
    public ResponseEntity getProgressChallenge(@RequestParam("userId") Long userId) {
        List<ChallengePreviewDto> progressChallenges = challengeService.getProgressChallenges(userId);
        return new ResponseEntity<>(progressChallenges, httpHeaders, HttpStatus.OK);
    }

    @Operation(summary = "챌린지 상세 정보 보기", description = "챌린지의 상세한 정보를 반환합니다.")
    @Parameters({
            @Parameter(name = "challengeId", description = "챌린지 아이디", example = "2"),
            @Parameter(name = "userId", description = "유저 아이디", example = "123")
    })
    @ApiResponse(responseCode = "200", description = "호출 성공", content = @Content(schema = @Schema(implementation = ChallengeDetailDto.class)))
    @GetMapping("/challenge-detail")
    public ResponseEntity getChallengeDetail(@RequestParam("challengeId") Long challengeId, @RequestParam("userId") Long userId) {
        ChallengeDetailDto challenge = challengeService.getChallengeDetail(challengeId, userId);
        return new ResponseEntity<>(challenge, httpHeaders, HttpStatus.OK);
    }

    // 챌린지를 중도 포기할 때나 챌린지 조건에 도달하여 성공하였을 때
    @Operation(summary = "챌린지의 상태 변경하기", description = "챌린지 중도 포기 및 성공 시에 따라 챌린지의 상태를 변경합니다. (챌린지 시작 전 : ‘N’, 챌린지 진행 중 : ‘P’, 챌린지 완료 ‘C’)")
    @ApiResponse(responseCode = "200", description = "호출 성공",  content = @Content(schema = @Schema(implementation = com.whyranoid.walkie.dto.response.ApiResponse.class)))
    @PostMapping("/challenge-detail/update-status")
    public ResponseEntity updateChallengeStatus(
            @RequestBody ChallengeStatusChangeRequest challengeStatusChangeRequest
    ) {
        return ResponseEntity.ok(challengeService.updateChallengeStatus(challengeStatusChangeRequest));
    }

    // 챌린지를 새로 시작하는 것으로 ChallengeStatus를 새로 만들어서 추가해야 함.
    @Operation(summary = "챌린지의 시작하기", description = "챌린지를 시작합니다.")
    @ApiResponse(responseCode = "200", description = "호출 성공",  content = @Content(schema = @Schema(implementation = com.whyranoid.walkie.dto.response.ApiResponse.class)))
    @PostMapping("/challenge-detail/start")
    public ResponseEntity startChallenge(@RequestBody ChallengeStatusCreateRequest challengeStatusCreateRequest) {
        return ResponseEntity.ok(challengeService.createChallengeStatus(challengeStatusCreateRequest));
    }
}