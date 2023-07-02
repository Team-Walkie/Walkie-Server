package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.dto.ChallengeDetailDto;
import com.whyranoid.walkie.dto.request.ChallengeStatusChangeRequest;
import com.whyranoid.walkie.dto.request.ChallengeStatusCreateRequest;
import com.whyranoid.walkie.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge")
public class ChallengeController {
    HttpHeaders httpHeaders = new HttpHeaders();
    private final ChallengeService challengeService;

    @GetMapping("/challenges/new")
    public ResponseEntity getNewChallenges(@RequestParam("userId") Long userId) {
        List<Challenge> challenges = challengeService.getNewChallenges(userId);
        return new ResponseEntity<>(challenges, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/challenges/top-rank")
    public ResponseEntity getPopularChallenges() {
        List<Challenge> challenges = challengeService.getPopularChallenges();
        return new ResponseEntity<>(challenges, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/challenges/category")
    public ResponseEntity getChallengesByCategory(@RequestParam("userId") Long userId, @RequestParam("category") char category) {
        List<Challenge> challenges = challengeService.getChallengesByCategory(userId, category);
        return new ResponseEntity<>(challenges, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/challenges/progress")
    public ResponseEntity getProgressChallenge(@RequestParam("userId") Long userId) {
        List<Challenge> progressChallenges = challengeService.getProgressChallenges(userId);
        return new ResponseEntity<>(progressChallenges, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/challenge-detail")
    public ResponseEntity getChallengeDetail(@RequestParam("challengeId") Long challengeId, @RequestParam("userId") Long userId) {
        ChallengeDetailDto challenge = challengeService.getChallengeDetail(challengeId, userId);
        return new ResponseEntity<>(challenge, httpHeaders, HttpStatus.OK);
    }

    // 챌린지를 중도 포기할 때나 챌린지 조건에 도달하여 성공하였을 때
    @PostMapping("/challenge-detail/update-status")
    public ResponseEntity updateChallengeStatus(@RequestBody ChallengeStatusChangeRequest challengeStatusChangeRequest) {
        return ResponseEntity.ok(challengeService.updateChallengeStatus(challengeStatusChangeRequest));
    }

    // 챌린지를 새로 시작하는 것으로 ChallengeStatus를 새로 만들어서 추가해야 함.
    @PostMapping("/challenge-detail/start")
    public ResponseEntity startChallenge(@RequestBody ChallengeStatusCreateRequest challengeStatusCreateRequest) {
        return ResponseEntity.ok(challengeService.createChallengeStatus(challengeStatusCreateRequest));
    }
}