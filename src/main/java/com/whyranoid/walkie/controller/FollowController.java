package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.service.FollowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FollowController")
@RequiredArgsConstructor
@RequestMapping("/api/follow")
@RestController
public class FollowController {

    private final FollowService followService;

    @GetMapping("/{walkie}/following")
    public ResponseEntity<Object> getFollowingList(@PathVariable Long walkie) {
        return ResponseEntity.ok(followService.getFollowingList(walkie));
    }

    @GetMapping("/{walkie}/follower")
    public ResponseEntity<Object> getFollowerList(@PathVariable Long walkie) {
        return ResponseEntity.ok(followService.getFollowerList(walkie));
    }
}
