package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Post;
import com.whyranoid.walkie.domain.PostLike;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.repository.querydsl.PostLikeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>, PostLikeRepositoryCustom {

    List<PostLike> findByPostAndLiker(Post post, Walkie liker);
}
