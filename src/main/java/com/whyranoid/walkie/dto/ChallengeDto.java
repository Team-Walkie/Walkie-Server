package com.whyranoid.walkie.dto;

import com.whyranoid.walkie.domain.Badge;
import com.whyranoid.walkie.domain.Challenge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeDto {
    @Schema(example = "2")
    private Long challengeId;
    @Schema(example = "L")
    private Character category;
    private Badge badge;
    @Schema(example = "햄버거 세트의 평균 칼로리는 1110kcal 입니다. 일주일 동안 걷기로 햄버거 세트 태우기 도전!")
    private String content;
    @Schema(example = "햄버거 세트 불태우기")
    private String name;
    @Schema(example = "challenge_img2")
    private String img;
    @Schema(example = "0")
    private Character status;
    @Schema(example = "N")
    private Integer progress;

    public ChallengeDto(Challenge challenge) {
        this.challengeId = challenge.getChallengeId();
        this.category = challenge.getCategory();
        this.badge = challenge.getBadge();
        this.content = challenge.getContent();
        this.name = challenge.getName();
        this.img = challenge.getImg();
    }
}
