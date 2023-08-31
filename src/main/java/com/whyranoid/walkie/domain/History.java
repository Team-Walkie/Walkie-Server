package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Column(nullable = false)
    private Double distance;

    @NotNull
    @Column(nullable = false)
    private String date;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private String startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private String endTime;

    @NotNull
    @Column(name = "total_time", nullable = false)
    private Integer totalTime;

    @NotNull
    @Column(nullable = false)
    private Integer calorie;

    @NotNull
    @Column(nullable = false)
    private Integer step;

    @NotNull
    @Column(nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Walkie user;
}
