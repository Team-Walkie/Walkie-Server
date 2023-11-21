package com.whyranoid.walkie.repository;

import com.whyranoid.walkie.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
