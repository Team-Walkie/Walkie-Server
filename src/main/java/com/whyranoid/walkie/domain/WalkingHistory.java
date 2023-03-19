package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalkingHistory {
    @Id
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    // endTime은 date와 동일?
    @NotNull
    @Column(name = "date", nullable = false)
    private Date date;

    @NotNull
    @Column(name = "distance", nullable = false)
    private Double distance;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    // 비즈니스 로직에서 시간, 분, 초로 계산해서 사용
    @Column(name = "total_time", nullable = false)
    private Long totalTime;

    @Column(name = "calorie", nullable = false)
    private Integer calorie;

    @Column(name = "step", nullable = false)
    private Integer step;

    // 이거 의논해봐야 할듯
//    @Column(name = "path", nullable = false)
//    private Long path;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Walkie user;
}
