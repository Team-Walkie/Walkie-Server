package com.whyranoid.walkie.api;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge")
public class ChallengeApi {
    HttpHeaders httpHeaders = new HttpHeaders();
    private final ChallengeService challengeService;

    @GetMapping("/newChallenges")
    public ResponseEntity getChallenges(@RequestParam("userId") Long userId) {
        // 본인 정보를 가져오는 로직이 필요
        List<Challenge> newChallenges = challengeService.getChallenges(userId, 'N');
        return new ResponseEntity<>(newChallenges, httpHeaders, HttpStatus.OK);
    }
}