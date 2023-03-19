package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter // 클래스 레벨에 선언할 경우 모든 필드에 접근자 자동 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 생성
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

//    // 필요 없는 속성인 것 같아서 주석처리 했슴당
//    @OneToMany
//    @JoinTable(name = "walkie_post",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "post_id")  // 외래키로 사용 가능한 속성이어야 함
//    )
//    private List<Post> posts = new ArrayList<>();

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public void setStatus(Character status) {
        this.status = status;
    }
}