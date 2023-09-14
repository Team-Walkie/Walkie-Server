package com.whyranoid.walkie.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.whyranoid.walkie.domain.QPostLike.postLike;

@RequiredArgsConstructor
public class PostLikeRepositoryImpl implements PostLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findPostLikeCount(Long postId) {
        return queryFactory
                .select(postLike)
                .from(postLike)
                .where(postLike.post.postId.eq(postId)
                        .and(postLike.liker.isNotNull()))
                .stream().count();
    }

//    @Override
//    public PostLikeDto findPostLikePeople(Long postId) {
//        return PostLikeDto.builder()
//                .postId(postId)
//                .likerCount(findPostLikeCount(postId))
//                .likerProfiles(findLikerList(postId))
//                .build();
//    }
//
//    public List<WalkieDto> findLikerList(Long postId) {
//        return queryFactory
//                .select(new QWalkieDto(postLike.liker))
//                .from(postLike)
//                .where(postLike.post.postId.eq(postId))
//                .fetch();
//    }
}
