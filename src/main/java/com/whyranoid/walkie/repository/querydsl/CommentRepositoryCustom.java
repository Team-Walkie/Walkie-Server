package com.whyranoid.walkie.repository.querydsl;

import com.whyranoid.walkie.dto.CommentDto;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentDto> findByPostId(Long postId);
}
