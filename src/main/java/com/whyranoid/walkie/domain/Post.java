package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    private String date;

    @NotNull
    @Column(name = "color_mode", nullable = false)
    private Integer colorMode;

    @NotNull
    @Column(name = "history_content", nullable = true)
    private String historyContent;

    @ManyToOne
    @JoinColumn(name = "walkie_id", nullable = false) // referencedColumnName는 디폴트가 pk
    private Walkie user;

    @ManyToMany
    @JoinTable(name = "walkie_post_like",
            joinColumns = @JoinColumn(name = "post_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "walkie_id", nullable = false))
    private List<Walkie> liker = new ArrayList<>();

//    @OneToOne(optional = false) // optional = false 안해주면 history가 겹치는 경우가 발생 시 에러
//    @JoinColumn(name = "history", nullable = false)
//    private History history;
}