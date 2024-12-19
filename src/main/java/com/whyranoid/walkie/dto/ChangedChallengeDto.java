package com.whyranoid.walkie.dto;

import com.whyranoid.walkie.dto.response.ChallengePreviewDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChangedChallengeDto {

    @Schema(description = "[응답] 달성에 실패해 삭제된 챌린지 리스트")
    List<ChallengePreviewDto> failedChallenges;

    @Schema(description = "[응답] 달성완료 처리된 챌린지 리스트")
    List<ChallengePreviewDto> completedChallenges;

    @Schema(description = "[응답] 진행 중인 챌린지 리스트")
    List<ChallengePreviewDto> ongoingChallenges;

    @Builder
    public ChangedChallengeDto(List<ChallengePreviewDto> failedChallenges, List<ChallengePreviewDto> completedChallenges, List<ChallengePreviewDto> ongoingChallenges) {
        this.failedChallenges = failedChallenges;
        this.completedChallenges = completedChallenges;
        this.ongoingChallenges = ongoingChallenges;
    }
}
