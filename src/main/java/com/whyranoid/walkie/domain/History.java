package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "total_time")
    private Integer totalTime;

    private Integer calorie;

    private Integer step;

    private String path;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Walkie user;
}
