package com.whyranoid.walkie.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Follow {

    @Id
    @Column(name = "following_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower")
    private Walkie follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed")
    private Walkie followed;

    @Builder
    public Follow(Walkie follower, Walkie followed) {
        this.follower = follower;
        this.followed = followed;
    }
}
