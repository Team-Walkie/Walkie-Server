package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.dto.request.WalkingStartRequest;
import com.whyranoid.walkie.dto.response.WalkingStartResponse;
import com.whyranoid.walkie.service.WalkingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "WalkingController")
@RequiredArgsConstructor
@RequestMapping("/api/walk")
@RestController
public class WalkingController {

    private final WalkingService walkingService;

    @PutMapping("/start")
    public WalkingStartResponse startWalking(@RequestBody WalkingDto walkingDto) {
        return walkingService.startWalking(walkingDto);
    }
}
