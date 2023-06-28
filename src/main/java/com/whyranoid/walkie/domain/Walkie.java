package com.whyranoid.walkie.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter // 클래스 레벨에 선언할 경우 모든 필드에 접근자 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자 생성
public class Walkie {
    @Id // Entity의 primary key임을 명시
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임, mysql의 경우 auto-increment가 기본
    private Long userId;

    @NotNull
    @Column(name = "user_name", nullable = false)
    @Size(min = 1, max = 15) // 임의로 지정해 놓은 것
    private String userName;

    @Column(name = "profile_img")
    private String profileImg;

    @NotNull
    @Column(name = "status", nullable = false)
    private Character status;

    @NotNull
    @Column(name = "auth_id", nullable = false)
    private String authId;

    // Walkie 객체가 아니라 고유 유저 id로 관리하는게 맞는걸까?
    @OneToMany(mappedBy = "following")
    private List<Walkie> following;

    @OneToMany(mappedBy = "follower")
    private List<Walkie> follower;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "badge_collection",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "badge_id", nullable = false)
    )
    private List<Badge> badges = new ArrayList<>();

    @Builder
    private Walkie(String userName, String profileImg, Character status, String authId) {
        this.userName = userName;
        this.profileImg = profileImg;
        this.status = status;
        this.authId = authId;
    }

    public void changeStatus(Character status) {
        this.status = status;
    }
}
