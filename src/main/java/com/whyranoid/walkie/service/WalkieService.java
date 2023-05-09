package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.repository.WalkieRepository;
import com.whyranoid.walkie.dto.request.WalkieSignUpRequest;
import com.whyranoid.walkie.dto.response.WalkieSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WalkieService {

    private final WalkieRepository walkieRepository;

    public WalkieSignUpResponse joinWalkie(WalkieSignUpRequest walkieSignUpRequest) {
        if (checkNameDuplication(walkieSignUpRequest.getUserName())) {
            return WalkieSignUpResponse.builder()
                    .hasDuplicatedName(true)
                    .build();
        }

        final Walkie walkie = walkieRepository.save(
                Walkie.builder()
                        .userName(walkieSignUpRequest.getUserName())
                        .profileImg(walkieSignUpRequest.getProfileImg())
                        .authId(walkieSignUpRequest.getAuthId())
                        .status('x')
                        .build()
        );

        return WalkieSignUpResponse.builder()
                .hasDuplicatedName(false)
                .accessToken("아이디=" + walkie.getUserId())
                .refreshToken("상태=" + walkie.getStatus())
                .build();
    }

    public boolean checkNameDuplication(String preName) {
        return walkieRepository.findByUserName(preName).isPresent();
    }
}
