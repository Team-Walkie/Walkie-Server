package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
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
    public ResponseEntity getChallenges(@RequestParam("userId") Long userId) {
        // 본인 정보를 가져오는 로직이 필요
        List<Challenge> newChallenges = challengeService.getChallenges(userId, 'N');
        return new ResponseEntity<>(newChallenges, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/challenge-detail")
    public ResponseEntity getChallengeDetail(@RequestParam("challengeId") Long challengeId, @RequestParam("userId") Long userId) {
        List<ChallengeStatus> challenge = challengeService.getChallengeDetail(challengeId, userId);
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