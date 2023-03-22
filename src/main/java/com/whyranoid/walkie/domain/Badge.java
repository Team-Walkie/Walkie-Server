package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Badge {

    @Id
    @Column(name = "badge_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long badgeId;

    @NotNull
    @Column(name = "badge_img", nullable = false)
    private String img;

    @ManyToMany(mappedBy = "badges", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Walkie> collectors = new ArrayList<>();
}
