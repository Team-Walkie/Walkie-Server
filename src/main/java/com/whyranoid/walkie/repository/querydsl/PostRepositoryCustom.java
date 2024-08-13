package com.whyranoid.walkie.repository.querydsl;

import com.querydsl.jpa.JPQLQuery;
import com.whyranoid.walkie.dto.PostDto;

import java.util.List;

public interface PostRepositoryCustom {

    List<PostDto> findCurrentPosts(JPQLQuery<Long> following, Long viewerId, Integer pagingSize, Integer pagingStart);

    List<PostDto> findMyPosts(Long viewerId, Integer pagingSize, Integer pagingStart);

    List<PostDto> findEveryPosts(Long viewerId, Integer pagingSize, Integer pagingStart);

    Long findPostId(String photo, String date);
}
