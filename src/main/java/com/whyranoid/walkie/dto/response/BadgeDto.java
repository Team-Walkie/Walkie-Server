package com.whyranoid.walkie.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BadgeDto {
    @Schema(example = "2")
    private Long badgeId;
    @Schema(example = "badge_image2")
    private String badgeImg;
    @Schema(example = "햄버거 세트 배애찌")
    private String badgeName;
    @Schema(example = "20230801(이거 localdatetime으로 넘겨주세용)")
    private String receivedAt;

}
