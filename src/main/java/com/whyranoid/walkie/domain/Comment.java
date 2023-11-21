package com.whyranoid.walkie.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotNull
    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "walkie_id", nullable = false)
    private Walkie user;

    @Builder
    public Comment(Long commentId, String date, String content, Post post, Walkie user) {
        this.commentId = commentId;
        this.date = date;
        this.content = content;
        this.post = post;
        this.user = user;
    }
}
