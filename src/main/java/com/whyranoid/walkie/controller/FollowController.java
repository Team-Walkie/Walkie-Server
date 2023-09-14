package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.dto.FollowDto;
import com.whyranoid.walkie.dto.WalkieDto;
import com.whyranoid.walkie.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "FollowController")
@RequiredArgsConstructor
@RequestMapping("/api/follow")
@RestController
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "팔로우하기")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FollowDto.class)),
            description = "성공 시 요청한 정보를 그대로 리턴")
    @PostMapping("/follow")
    public ResponseEntity<FollowDto> follow(@RequestBody FollowDto followRequest) {
        return ResponseEntity.ok(followService.doFollow(followRequest));
    }

    @Operation(summary = "언팔로우하기")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FollowDto.class)),
            description = "성공 시 요청 정보에 unfollow = follow를 추가해 리턴")
    @DeleteMapping("/unfollow")
    public ResponseEntity<FollowDto> unfollow(@RequestBody FollowDto followRequest) {
        return ResponseEntity.ok(followService.doUnfollow(followRequest));
    }

    @Operation(summary = "내가 팔로우하는 사람 리스트 가져오기")
    @Parameter(name = "워키 아이디", required = true, description = "팔로잉 리스트를 가져올 유저의 id", example = "3")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = WalkieDto.class))),
            description = "성공 시 id가 팔로우하는 유저 정보의 리스트를 반환")
    @GetMapping("/{walkie}/following")
    public ResponseEntity<List<WalkieDto>> getFollowingList(@PathVariable("walkie") Long walkieId) {
        return ResponseEntity.ok(followService.getFollowingList(walkieId));
    }

    @Operation(summary = "나를 팔로우하는 사람 리스트 가져오기")
    @Parameter(name = "워키 아이디", required = true, description = "팔로워 리스트를 가져올 유저의 id", example = "3")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = WalkieDto.class))),
            description = "성공 시 id를 팔로우하는 유저 정보의 리스트를 반환")
    @GetMapping("/{walkie}/follower")
    public ResponseEntity<List<WalkieDto>> getFollowerList(@PathVariable("walkie") Long walkieId) {
        return ResponseEntity.ok(followService.getFollowerList(walkieId));
    }

    @Operation(summary = "운동 중인 친구 목록 조회")
    @Parameter(name = "워키 아이디", required = true, description = "운동 중인 친구 목록을 가져올 유저의 id", example = "3")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = WalkieDto.class))),
            description = "성공 시 id가 팔로우 하고 운동 중인 유저 정보의 리스트를 반환")
    @GetMapping("/{walkie}/walking-followings")
    public ResponseEntity<List<WalkieDto>> getWalkingFollowingList(@PathVariable("walkie") Long walkieId) {
        return ResponseEntity.ok(followService.getWalkingFollowingList(walkieId));
    }
}
