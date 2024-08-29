package com.whyranoid.walkie.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter // 클래스 레벨에 선언할 경우 모든 필드에 접근자 자동 생성
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자 생성
public class Walkie {
    @Id // Entity의 primary key임을 명시
    @Column(name = "walkie_id")
    @Schema(example = "123")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임, mysql의 경우 auto-increment가 기본
    private Long userId;

    @NotNull
    @Column(name = "user_name", nullable = false)
    @Size(min = 1, max = 15) // 임의로 지정해 놓은 것
    @Schema(example = "승민")
    private String userName;

//    @NotNull
    @Column(name = "name") // nullable = false
    @Size(max = 50)
    @Schema(example = "홍길동")
    private String name;

    @Column(name = "profile_img")
    @Schema(example = "seungmin_profile_img")
    private String profileImg;

    @NotNull
    @Column(name = "status", nullable = false)
    @Schema(example = "N")
    private Character status;

    @NotNull
    @Column(name = "auth_id", nullable = false)
    private String authId;

//    @ManyToMany(cascade = CascadeType.REMOVE)
//    @JoinTable(
//            name = "badge_collection",
//            joinColumns = @JoinColumn(name = "user_id", nullable = false),
//            inverseJoinColumns = @JoinColumn(name = "badge_id", nullable = false)
//    )
//    private List<Badge> badges = new ArrayList<>();

    @Builder
    private Walkie(String userName, String name, String profileImg, Character status, String authId) {
        this.userName = userName;
        this.name = name;
        this.profileImg = profileImg;
        this.status = status;
        this.authId = authId;
    }

    public void changeStatus(Character status) {
        this.status = status;
    }
}
