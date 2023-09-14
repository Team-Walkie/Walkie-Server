package com.whyranoid.walkie.repository.querydsl;

public interface PostLikeRepositoryCustom {

    Long findPostLikeCount(Long postId);

//    PostLikeDto findPostLikePeople(Long postId);
}
