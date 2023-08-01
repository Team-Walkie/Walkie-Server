package com.whyranoid.walkie.service;

import com.whyranoid.walkie.dto.response.BadgeDto;
import com.whyranoid.walkie.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;

    public List<BadgeDto> getBadges(Long userId) {
        return badgeRepository.getBadges(userId);
    }
}
