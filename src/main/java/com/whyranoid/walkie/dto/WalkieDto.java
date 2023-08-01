package com.whyranoid.walkie.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.whyranoid.walkie.domain.Walkie;
import lombok.Getter;

@Getter
public class WalkieDto {

    private Long uid;
    private String name;
    private String profileImg;
    private Character status;

    @QueryProjection
    public WalkieDto(Walkie walkie) {
        this.uid = walkie.getUserId();
        this.name = walkie.getUserName();
        this.profileImg = walkie.getProfileImg();
        this.status = walkie.getStatus();
    }
}
