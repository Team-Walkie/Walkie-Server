package com.whyranoid.walkie.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChallengePreviewDto {

    @Schema(example = "1")
    private Long challengeId;

    @Schema(example = "C")
    private Character category;

    @Schema(example = "햄버거 세트 불태우기")
    private String name;

    @Schema(example = "N")
    private Character status;

    @Schema(example = "0")
    private Integer progress;

    @Schema(example = "1")
    private Integer newFlag;
}
