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
        List<Long> badgeIds = badgeRepository.getBadgeIds(badgeDto.getWalkieId());

        if (badgeIds.contains(badgeDto.getBadgeId())) {
            return ApiResponse.builder()
                    .status(200)
                    .message("이미 획득한 배지입니다")
                    .build();
        }

        BadgeCollection bc = new BadgeCollection();
        bc.setWalkieId(badgeDto.getWalkieId());
        bc.setBadgeId(badgeDto.getBadgeId());
        bc.setReceivedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        bc.setIsRep(badgeIds.size() < 5);
        badgeRepository.obtainBadge(bc);
        return ApiResponse.builder()
                .status(200)
                .message("배지 획득!")
                .build();
    }

    public ApiResponse updateBadgeIndices(BadgeDto badgeDto) {
        Long walkieId = badgeDto.getWalkieId();
        List<Long> badgeIdList = badgeDto.getBadgeIdList();

        if (badgeIdList.isEmpty()) badgeIdList.add(-1L);
        badgeRepository.updateBadgeIndices(walkieId, badgeIdList);

        return ApiResponse.builder()
                .status(200)
                .message("대표뱃지 설정 업데이트 완료")
                .build();
    }
}
