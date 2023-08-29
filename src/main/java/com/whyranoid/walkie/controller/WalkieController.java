package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.dto.request.MyInfoRequest;
import com.whyranoid.walkie.dto.request.WalkieSignUpRequest;
import com.whyranoid.walkie.dto.response.MyInfoResponse;
import com.whyranoid.walkie.dto.response.WalkieSignUpResponse;
import com.whyranoid.walkie.service.WalkieService;
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

    @Operation(summary = "내 정보 불러오기", description = "마이페이지에서 내 정보를 불러오는 api")
    @Parameters({
            @Parameter(name = "walkieId", required = true, description = "내 walkieId", example = "123")
    })
    @GetMapping("/my")
    public ResponseEntity<MyInfoResponse> getMyInfo(@RequestParam Long walkieId) {
        return ResponseEntity.ok(
                walkieService.getMyInfo(walkieId)
        );
    }

    @Operation(summary = "내 정보 변경하기", description = "마이페이지에서 내 정보를 수정하는 api")
    @Parameters({
            @Parameter(name = "walkieId", required = true, description = "내 walkieId", example = "123")
    })
    @PostMapping("/my")
    public ResponseEntity<MyInfoResponse> changeMyInfo(@RequestParam Long walkieId, @RequestBody MyInfoRequest myInfoRequest) {
        return ResponseEntity.ok(
                walkieService.changeMyInfo(walkieId, myInfoRequest)
        );
    }
}
