package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.dto.response.BadgeDto;
import com.whyranoid.walkie.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "badge", description = "배지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/badge")
public class BadgeController {
    HttpHeaders httpHeaders = new HttpHeaders();

    private final BadgeService badgeService;

    @Operation(summary = "유저 아이디로 배지 가져오기", description = "유저 아이디를 통해 배지를 해당 유저가 획득한 배지들을 조회합니다.")
    @Parameters({
            @Parameter(name = "walkieId", description = "유저 아이디", example = "123")
    })
    @ApiResponse(responseCode = "200", description = "호출 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BadgeDto.class))))
    @GetMapping("/badges")
    public ResponseEntity getBadges(@RequestParam("walkieId") Long walkieId) {
        List<BadgeDto> badges = badgeService.getBadges(walkieId);
        return new ResponseEntity<>(badges, httpHeaders, HttpStatus.OK);
    }

    @Operation(summary = "배지 획득하기", description = "챌린지 조건에 만족할 때 해당 api를 호출하면 유저가 배지를 획득합니다.")
    @Parameters({
            @Parameter(name = "walkieId", description = "유저 아이디", example = "123"),
            @Parameter(name = "badgeId", description = "배지 아이디", example = "3")
    })
    @ApiResponse(responseCode = "200", description = "호출 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = com.whyranoid.walkie.dto.response.ApiResponse.class))))

    @PostMapping("/obtain-badge")
    public ResponseEntity obtainBadge(@RequestBody BadgeDto badgeDto) {
        return ResponseEntity.ok(badgeService.obtainBadge(badgeDto));   //TODO: 중복방지
    }

    @Operation(summary = "전체 뱃지 순서 업데이트", description = "전체 뱃지의 순서를 badgeIdList 대로 업데이트합니다.")
    @ApiResponse(responseCode = "200", description = "호출 성공", content = @Content(schema = @Schema(implementation = BadgeDto.class)))
    @PostMapping("/update-badge-indices")
    public ResponseEntity updateBadgeIndices(@RequestBody BadgeDto badgeDto) {
        return ResponseEntity.ok(badgeService.updateBadgeIndices(badgeDto));
    }
}
