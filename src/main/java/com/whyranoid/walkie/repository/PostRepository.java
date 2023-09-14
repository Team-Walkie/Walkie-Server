package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostId(Long postId);
}
