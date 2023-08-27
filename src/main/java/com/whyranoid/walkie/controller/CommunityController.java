package com.whyranoid.walkie.controller;

import com.whyranoid.walkie.dto.request.PostRequest;
import com.whyranoid.walkie.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "community", description = "커뮤니티 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {
    HttpHeaders httpHeaders = new HttpHeaders();

    private final CommunityService communityService;
    @Operation(summary = "게시글 올리기", description = "커뮤니티에 게시글을 업로드합니다.")
    @ApiResponse(responseCode = "200", description = "업로드 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = com.whyranoid.walkie.dto.response.ApiResponse.class))))
    @PostMapping("/upload-post")
    public ResponseEntity uploadPost(
        @RequestBody PostRequest postRequest
    ) {
        return ResponseEntity.ok(communityService.uploadPost(postRequest));
    }

}
