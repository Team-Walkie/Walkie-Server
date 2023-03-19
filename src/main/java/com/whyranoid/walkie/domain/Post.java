package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotNull
    @Column(name = "photo", nullable = false)
    private String photo;

    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // referencedColumnName는 디폴트가 pk
    private Walkie user;

    @ManyToMany
    @JoinTable(name = "walkie_post_like",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Walkie> liker = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "history_id", nullable = false)
    private WalkingHistory walkingHistory;
}
