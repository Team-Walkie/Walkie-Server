package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.service.WalkieService;
import com.whyranoid.walkie.dto.request.WalkieSignUpRequest;
import com.whyranoid.walkie.dto.response.WalkieSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/walkies")
@RestController
public class WalkieController {

    private final WalkieService walkieService;

    @PostMapping("/signup")
    public ResponseEntity<WalkieSignUpResponse> signUp(@RequestBody WalkieSignUpRequest walkieSignUpRequest) {
        return ResponseEntity.ok(walkieService.joinWalkie(walkieSignUpRequest));
    }
}
