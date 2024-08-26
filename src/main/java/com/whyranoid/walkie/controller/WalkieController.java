package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.dto.PostDto;
import com.whyranoid.walkie.dto.request.MyInfoRequest;
import com.whyranoid.walkie.dto.request.WalkieSignUpRequest;
import com.whyranoid.walkie.dto.response.MyInfoResponse;
import com.whyranoid.walkie.dto.response.WalkieLogInResponse;
import com.whyranoid.walkie.dto.response.WalkieSignUpResponse;
import com.whyranoid.walkie.service.WalkieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;

@Tag(name = "WalkieController")
@RequiredArgsConstructor
@RequestMapping("/api/walkies")
@RestController
public class WalkieController {

    private final WalkieService walkieService;

    @Operation(summary = "회원가입", description = "소셜 로그인 이후 워키 회원가입 요청")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = WalkieSignUpResponse.class)),
            description = "성공 시 요청한 아이디, 닉네임과 hasDuplicated=false를, 닉네임 중복 시 hasDuplicated=true를 반환")
    @PostMapping("/signup")
    public ResponseEntity<WalkieSignUpResponse> signUp(@RequestBody WalkieSignUpRequest walkieSignUpRequest) {
        return ResponseEntity.ok(walkieService.joinWalkie(walkieSignUpRequest));
    }

    @Operation(summary = "닉네임 중복 확인", description = "회원가입과 동일한 dto를 응답으로 사용")
    @Parameter(name = "userName", required = true, description = "닉네임", example = "군자동 불주먹")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = WalkieSignUpResponse.class)),
            description = "통과 시 false, 중복 시 true를 반환")
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

    @Operation(summary = "가입 여부 및 워키아이디 확인", description = "구글 uid를 통해 가입 여부와 워키 아이디를 확인")
    @Parameter(name = "uid", required = true, description = "구글 uid", example = "ja9438yweirushf")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = WalkieLogInResponse.class)),
            description = "가입했던 구글 uid라면 사용자 정보를 반환, 아니라면 null을 반환")
    @GetMapping("/login")
    public ResponseEntity<WalkieLogInResponse> getWalkieId(@RequestParam String uid) {
        return ResponseEntity.ok(walkieService.getWalkieId(uid));
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

    @Operation(summary = "나의 게시글 불러오기", description = "내가 작성한 게시글들을 가져옵니다.")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostDto.class))),
            description = "내가 작성한 게시글 정보 리스트를 반환")
    @Parameters({
            @Parameter(name = "walkieId", required = true, description = "유저 아이디", example = "123"),
            @Parameter(name = "pagingSize", description = "페이징 사이즈", example = "30"),
            @Parameter(name = "pagingStart", description = "페이징 오프셋", example = "0")
    })
    @GetMapping("/listup-my-post")
    public ResponseEntity<List<PostDto>> getPostList(@RequestParam Long walkieId, @RequestParam(required = false) Integer pagingSize, @RequestParam(required = false) Integer pagingStart) {
        return ResponseEntity.ok(walkieService.getMyPostList(walkieId, pagingSize, pagingStart));
    }

    @Operation(summary = "나의 게시글 수 가져오기", description = "내가 작성한 총 게시글 수를 가져옵니다.")
    @Parameters({
            @Parameter(name = "walkieId", required = true, description = "내 walkieId", example = "123")
    })
    @GetMapping("/count-my-post")
    public ResponseEntity<Integer> getPostCount(@RequestParam Long walkieId) {
        return ResponseEntity.ok(walkieService.getMyPostCount(walkieId));
    }

}
