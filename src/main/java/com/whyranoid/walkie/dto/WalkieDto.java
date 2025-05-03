package com.whyranoid.walkie.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.whyranoid.walkie.domain.Walkie;
import lombok.Getter;

@Getter
public class WalkieDto {

    private Long walkieId;
    private String nickname;
    private String profileImg;
    private Character status;

    @QueryProjection
    public WalkieDto(Walkie walkie) {
        if (walkie != null) {
            this.walkieId = walkie.getUserId();
            this.nickname = walkie.getUserName();
            this.profileImg = walkie.getProfileImg();
            this.status = walkie.getStatus();
        } else {
            this.walkieId = null;
            this.nickname = null;
            this.profileImg = null;
            this.status = null;
        }
    }

    @QueryProjection
    public WalkieDto(Long walkieId, String nickname, String profileImg, Character status) {
        this.walkieId = walkieId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.status = status;
    }
}
