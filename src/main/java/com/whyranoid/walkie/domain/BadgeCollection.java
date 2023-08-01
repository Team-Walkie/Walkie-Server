package com.whyranoid.walkie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeCollection {

    @Id
    @Column(name = "collection_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectionId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "badge_id", nullable = false)
    private Long badgeId;

    @NotNull
    @Column(name = "received_at", nullable = false)
    private String receivedAt;
}
