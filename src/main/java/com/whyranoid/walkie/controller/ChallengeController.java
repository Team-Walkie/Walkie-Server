package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
import com.whyranoid.walkie.dto.request.ChallengeStatusChangeRequest;
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

    @PostMapping("/challenge-detail/start")
    public ResponseEntity startChallenge(@RequestBody ChallengeStatusChangeRequest challengeStatusChangeRequest) {
        return ResponseEntity.ok(challengeService.updateChallengeStatus(challengeStatusChangeRequest));
    }
}