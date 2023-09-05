package com.whyranoid.walkie.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.whyranoid.walkie.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "community", description = "커뮤니티 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {
    HttpHeaders httpHeaders = new HttpHeaders();

    private final CommunityService communityService;
    @Operation(summary = "게시글 올리기", description = "커뮤니티에 게시글을 업로드합니다. colorMode는 글자색으로 다음과 같이 표현합니다. -> (0: 검정색, 1: 흰색) historyContent는 history 정보를 표현합니다. 없으면 null 넘겨주시면 돼요")
    @ApiResponse(responseCode = "200", description = "업로드 성공", content = @Content(schema = @Schema(implementation = com.whyranoid.walkie.dto.response.ApiResponse.class)))
    @Parameters({
            @Parameter(name = "image", description = "업로드할 이미지 multipart", example = "image.jpg"),
            @Parameter(name = "walkieId", description = "유저 아이디", example = "123"),
            @Parameter(name = "content", description = "유저가 쓴 글의 내용", example = "아 상쾌하게 달렸다!"),
            @Parameter(name = "colorMode", description = "글자색 카테고리(0은 검정, 1은 흰색)", example = "1"),
            @Parameter(name = "historyContent", description = "운동기록 정보", example = "7.51km 01:03:45 08'29\"")
    })
    @PostMapping(value = "/upload-post")
    public ResponseEntity uploadPost(
        @RequestParam("image")MultipartFile image,
        @RequestParam("walkieId")Long walkieId,
        @RequestParam("content")String content,
        @RequestParam("colorMode")Integer colorMode,
        @RequestParam("historyContent")String historyContent
    ) throws IOException, FirebaseAuthException {
        return ResponseEntity.ok(communityService.uploadPost(image, walkieId, content, colorMode, historyContent));
    }
}
