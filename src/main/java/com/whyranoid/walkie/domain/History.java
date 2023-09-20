package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Column(name = "total_time")
    private Integer totalTime;

    private Integer calorie;

    private Integer step;

    @ManyToOne
    @JoinColumn(name = "walkie_id", nullable = false)
    private Walkie user;
}
