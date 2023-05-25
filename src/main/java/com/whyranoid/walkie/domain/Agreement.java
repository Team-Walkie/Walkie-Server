package com.whyranoid.walkie.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agreement {

    @Id
    @Column(name = "agreement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agreementId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Walkie user;

    @Column(name = "gps_service", nullable = false)
    private Boolean gpsService;

    @Column(name = "subscribe", nullable = false)
    private Boolean subscription;

    @Builder
    public Agreement(Walkie user, Boolean gpsService, Boolean subscription) {
        this.user = user;
        this.gpsService = gpsService;
        this.subscription = subscription;
    }
}