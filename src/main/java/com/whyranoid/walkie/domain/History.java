package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @NotNull
    private Date date;

    @NotNull
    private Double distance;

    @NotNull
    @Column(name = "start_time")
    private Date startTime;

    @NotNull
    @Column(name = "end_time")
    private Date endTime;

    @NotNull
    @Column(name = "total_time")
    private int totalTime;

    @NotNull
    private Double pace;    // 이걸 빼자는 얘기가 있었음

    @NotNull
    private int calorie;

    @NotNull
    private int step;

    private String path;    // json 문자열? JSONArray?

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Walkie user;
}
