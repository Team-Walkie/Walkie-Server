package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Comment;
import com.whyranoid.walkie.repository.querydsl.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
