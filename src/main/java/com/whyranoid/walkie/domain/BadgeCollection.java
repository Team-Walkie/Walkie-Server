package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeCollection {

    @Id
    @Column(name = "collection_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectionId;

    @NotNull
    @Column(name = "walkie_id", nullable = false)
    private Long walkieId;

    @NotNull
    @Column(name = "badge_id", nullable = false)
    private Long badgeId;

    @NotNull
    @Column(name = "received_at", nullable = false)
    private String receivedAt;

    @Column(name = "is_rep", nullable = false)
    @ColumnDefault("false")
    private Boolean isRep;

    @Column(name = "position")
    private Integer position;
}
