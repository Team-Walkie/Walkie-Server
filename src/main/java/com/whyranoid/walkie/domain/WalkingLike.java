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
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class WalkingLike {

    @Id
    @Column(name = "walking_like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walkingLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history")
    private History history;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liker")
    private Walkie liker;

    @Builder
    public WalkingLike(Long walkingLikeId, History history, Walkie liker) {
        this.walkingLikeId = walkingLikeId;
        this.history = history;
        this.liker = liker;
    }
}
