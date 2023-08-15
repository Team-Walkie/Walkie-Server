package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.service.WalkieService;
import com.whyranoid.walkie.dto.request.WalkieSignUpRequest;
import com.whyranoid.walkie.dto.response.WalkieSignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "WalkieController")
@RequiredArgsConstructor
@RequestMapping("/api/walkies")
@RestController
public class WalkieController {

    private final WalkieService walkieService;

    @Operation(description = "소셜 로그인 이후 워키 회원가입 요청")
    @PostMapping("/signup")
    public ResponseEntity<WalkieSignUpResponse> signUp(@RequestBody WalkieSignUpRequest walkieSignUpRequest) {
        return ResponseEntity.ok(walkieService.joinWalkie(walkieSignUpRequest));
    }

    @Operation(description = "닉네임 중복 확인 -- 회원가입과 동일한 dto 사용")
    @Parameters({
            @Parameter(name = "userName", required = true, description = "닉네임", example = "군자동 불주먹")
    })
    @GetMapping("/signup/check")
    public ResponseEntity<WalkieSignUpResponse> check(@RequestParam String userName) {
        return ResponseEntity.ok(
                WalkieSignUpResponse.builder()
                        .hasDuplicatedName(
                                walkieService.checkNameDuplication(userName)
                        )
                        .build()
        );
    }
}
