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

    private Double distance;

    private String date;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "total_time")
    private Integer totalTime;

    private Integer calorie;

    private Integer step;

    @ManyToOne
    @JoinColumn(name = "walkie_id", nullable = false)
    private Walkie user;
}
