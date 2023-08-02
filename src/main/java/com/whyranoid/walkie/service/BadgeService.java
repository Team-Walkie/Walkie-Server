package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.BadgeCollection;
import com.whyranoid.walkie.dto.response.ApiResponse;
import com.whyranoid.walkie.dto.response.BadgeDto;
import com.whyranoid.walkie.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;

    public List<BadgeDto> getBadges(Long userId) {
        return badgeRepository.getBadges(userId);
    }

    public ApiResponse obtainBadge(Long userId, Long badgeId) {
        BadgeCollection bc = new BadgeCollection();
        bc.setUserId(userId);
        bc.setBadgeId(badgeId);
        bc.setReceivedAt(LocalDate.now().toString());
        badgeRepository.obtainBadge(bc);
        return ApiResponse.builder()
                .status(200)
                .message("배지 획득!")
                .build();
    }
}
