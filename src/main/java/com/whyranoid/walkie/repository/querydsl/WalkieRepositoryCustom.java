package com.whyranoid.walkie.repository.querydsl;

import com.whyranoid.walkie.dto.WalkieDto;

import java.util.List;

public interface WalkieRepositoryCustom {

    List<WalkieDto> findByUserNameMatched(String keyword);
}
