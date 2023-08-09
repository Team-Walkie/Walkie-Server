package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.dto.FollowDto;
import com.whyranoid.walkie.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "FollowController")
@RequiredArgsConstructor
@RequestMapping("/api/follow")
@RestController
public class FollowController {

    private final FollowService followService;

    @Operation(description = "팔로우하기")
    @PostMapping("/follow")
    public ResponseEntity<FollowDto> follow(@RequestBody FollowDto followRequest) {
        return ResponseEntity.ok(followService.doFollow(followRequest));
    }

    @Operation(description = "언팔로우하기")
    @DeleteMapping("/unfollow")
    public ResponseEntity<FollowDto> unfollow(@RequestBody FollowDto followRequest) {
        return ResponseEntity.ok(followService.doUnfollow(followRequest));
    }

    @Operation(description = "내가 팔로우하는 사람 리스트 가져오기")
    @Parameter(name = "워키 아이디", required = true, description = "팔로잉 리스트를 가져올 유저의 id", example = "3")
    @GetMapping("/{walkie}/following")
    public ResponseEntity<Object> getFollowingList(@PathVariable Long walkie) {
        return ResponseEntity.ok(followService.getFollowingList(walkie));
    }

    @Operation(description = "나를 팔로우하는 사람 리스트 가져오기")
    @Parameter(name = "워키 아이디", required = true, description = "팔로워 리스트를 가져올 유저의 id", example = "3")
    @GetMapping("/{walkie}/follower")
    public ResponseEntity<Object> getFollowerList(@PathVariable Long walkie) {
        return ResponseEntity.ok(followService.getFollowerList(walkie));
    }
}
