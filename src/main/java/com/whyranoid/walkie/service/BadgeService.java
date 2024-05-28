package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.BadgeCollection;
import com.whyranoid.walkie.dto.response.ApiResponse;
import com.whyranoid.walkie.dto.response.BadgeDto;
import com.whyranoid.walkie.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;

    public List<BadgeDto> getBadges(Long walkieId) {
        return badgeRepository.getBadges(walkieId);
    }

    public ApiResponse obtainBadge(BadgeDto badgeDto) {
        BadgeCollection bc = new BadgeCollection();
        bc.setWalkieId(badgeDto.getWalkieId());
        bc.setBadgeId(badgeDto.getBadgeId());
        bc.setReceivedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        bc.setIsRep(false);
        badgeRepository.obtainBadge(bc);
        return ApiResponse.builder()
                .status(200)
                .message("배지 획득!")
                .build();
    }

    public ApiResponse updateRepBadges(BadgeDto badgeDto) {
        Long walkieId = badgeDto.getWalkieId();
        List<Long> repBadgeIdList = badgeDto.getRepBadgeIdList();

        if (repBadgeIdList.isEmpty()) repBadgeIdList.add(-1L);
        badgeRepository.updateRepBadges(walkieId, repBadgeIdList);

        return ApiResponse.builder()
                .status(200)
                .message("대표뱃지 설정 업데이트 완료")
                .build();
    }
}
