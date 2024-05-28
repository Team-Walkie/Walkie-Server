package com.whyranoid.walkie.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BadgeDto {
    @Schema(description = "뱃지 아이디\n[REQ]obtain-badge\n[RES]badges", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Long badgeId;
    @Schema(description = "뱃지 이미지\n[REQ]\n[RES]badges", example = "badge_image2")
    private String badgeImg;
    @Schema(description = "뱃지 이름\n[REQ]\n[RES]badges", example = "햄버거 세트 배애찌")
    private String badgeName;
    @Schema(description = "뱃지 획득일시\n[REQ]\n[RES]badges", example = "2023-07-31 15:08:31")
    private String receivedAt;
    @Schema(description = "뱃지 대표여부\n[REQ]\n[RES]badges", example = "true")
    private Boolean isRep;
    @Schema(description = "뱃지 소유 유저 아이디\n[REQ]obtain-badge, update-badge-indices\n[RES]badges", requiredMode = Schema.RequiredMode.REQUIRED, example = "24")
    private Long walkieId;
    @Schema(description = "전체 뱃지 순서 아이디 리스트. 맨 앞 5개를 대표 뱃지로 간주\n[REQ]update-badge-indices\n[RES]", requiredMode = Schema.RequiredMode.REQUIRED, example = "[ 1, 5, 3 ]")
    private List<Long> badgeIdList;

    public BadgeDto(Long badgeId, String badgeImg, String badgeName, String receivedAt, Boolean isRep, Long walkieId) {
        this.badgeId = badgeId;
        this.badgeImg = badgeImg;
        this.badgeName = badgeName;
        this.receivedAt = receivedAt;
        this.isRep = isRep;
        this.walkieId = walkieId;
    }
}
