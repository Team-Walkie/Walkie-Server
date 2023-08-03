package com.whyranoid.walkie.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.whyranoid.walkie.domain.Follow;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {

    @Schema(description = "팔로우 신청 유저 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "24576")
    private Long followerId;

    @Schema(description = "팔로우 받은 유저 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "1608")
    private Long followedId;

    @Schema(description = "(검증용) 언팔로우 되었는지 여부", example = "false")
    private boolean unfollow;

    @QueryProjection
    public FollowDto(Follow follow, boolean unfollow) {
        this.followerId = follow.getFollower().getUserId();
        this.followedId = follow.getFollowed().getUserId();
        this.unfollow = unfollow;
    }

    public void setUnfollow(boolean unfollow) {
        this.unfollow = unfollow;
    }
}
