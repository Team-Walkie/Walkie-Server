package com.whyranoid.walkie.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.whyranoid.walkie.dto.CommentDto;
import com.whyranoid.walkie.dto.PostDto;
import com.whyranoid.walkie.dto.PostLikeDto;
import com.whyranoid.walkie.dto.WalkieDto;
import com.whyranoid.walkie.service.CommunityService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    @PostMapping(value = "/upload-post", consumes = {"multipart/form-data"})
    public ResponseEntity uploadPost(
        @RequestPart(value = "image", required = false) MultipartFile image,
        @RequestParam("walkieId")Long walkieId,
        @RequestParam("content")String content,
        @RequestParam("colorMode")Integer colorMode,
        @RequestParam("historyContent")String historyContent
    ) throws IOException, FirebaseAuthException {
        return ResponseEntity.ok(communityService.uploadPost(image, walkieId, content, colorMode, historyContent));
    }

    @Operation(summary = "게시글에 좋아요 누르기")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PostLikeDto.class)),
            description = "성공 시 요청에 게시글의 현재 좋아요 수를 넣어 반환, 중복 좋아요 시 좋아요를 삭제하고 likerCount에 -1을 넣어 반환, 실패 시 예외발생")
    @PostMapping("/send-like")
    public ResponseEntity<PostLikeDto> sendPostLike(@RequestBody PostLikeDto postLikeDto) {
        return ResponseEntity.ok(communityService.sendPostLike(postLikeDto));
    }

    @Operation(summary = "게시글 불러오기", description = "팔로우하는 유저들의 게시글을 가져옵니다.")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostDto.class))),
            description = "팔로우하는 유저들의 게시글 정보 리스트를 반환")
    @Parameters({
            @Parameter(name = "walkieId", required = true, description = "유저 아이디", example = "123"),
            @Parameter(name = "pagingSize", description = "페이징 사이즈", example = "30"),
            @Parameter(name = "pagingStart", description = "페이징 오프셋", example = "0")
    })
    @GetMapping("/listup-post")
    public ResponseEntity<List<PostDto>> getPostList(@RequestParam Long walkieId, @RequestParam(required = false) Integer pagingSize, @RequestParam(required = false) Integer pagingStart) {
        return ResponseEntity.ok(communityService.getPostList(walkieId, pagingSize, pagingStart));
    }

    @Operation(summary = "닉네임 검색결과 불러오기", description = "keyword로 시작하는 닉네임의 유저 리스트를 가져옵니다.")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = WalkieDto.class))),
            description = "닉네임이 keyword로 시작하는 유저 리스트를 반환")
    @Parameter(name = "keyword", required = true, description = "검색한 문자열. 문자열 뒤를 와일드카드 처리하여 검색한다", example = "군자")
    @GetMapping("/search-nickname")
    public ResponseEntity<List<WalkieDto>> getSearchResult(@RequestParam String keyword) {
        return ResponseEntity.ok(communityService.getSearchResult(keyword));
    }

    @Operation(summary = "게시글에 댓글 달기")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CommentDto.class)),
            description = "성공 시 댓글 생성 완료, 실패 시 예외 발생")
    @PostMapping("/write-comment")
    public ResponseEntity writeComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(communityService.writeComment(commentDto));
    }

    @Operation(summary = "댓글 불러오기", description = "특정 게시글의 댓글 리스트를 불러옵니다.")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDto.class))),
            description = "특정 게시글의 댓글 리스트를 반환")
    @Parameter(name = "postId", required = true, description = "게시글 pk", example = "123")
    @GetMapping("/listup-comment")
    public ResponseEntity<List<CommentDto>> getPostCommentList(@RequestParam Long postId) {
        return ResponseEntity.ok(communityService.getPostCommentList(postId));
    }

    @Operation(summary = "모든 게시글 불러오기", description = "전체 유저들의 게시글을 최신순으로 가져옵니다.")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostDto.class))),
            description = "전체 유저들의 게시글 정보 리스트를 반환")
    @Parameters({
            @Parameter(name = "walkieId", required = true, description = "유저 아이디", example = "123"),
            @Parameter(name = "pagingSize", description = "페이징 사이즈", example = "30"),
            @Parameter(name = "pagingStart", description = "페이징 오프셋", example = "0")
    })
    @GetMapping("/listup-every-post")
    public ResponseEntity<List<PostDto>> getEveryPostList(@RequestParam Long walkieId, @RequestParam(required = false) Integer pagingSize, @RequestParam(required = false) Integer pagingStart) {
        return ResponseEntity.ok(communityService.getEveryPostList(walkieId, pagingSize, pagingStart));
    }
}
