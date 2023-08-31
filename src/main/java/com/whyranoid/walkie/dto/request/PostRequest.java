package com.whyranoid.walkie.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostRequest {
    @NotNull
    @Schema(example = "123")
    private Long walkieId;

    @NotNull
    @Schema(example = "업로드할 사진 이미지 url")
    private String photo;

    @NotNull
    @Schema(example = "오늘 진짜 많이 걸었다~~")
    private String content;

    @Builder
    public PostRequest(Long walkieId, String photo, String content) {
        this.walkieId = walkieId;
        this.photo = photo;
        this.content = content;
    }
}
