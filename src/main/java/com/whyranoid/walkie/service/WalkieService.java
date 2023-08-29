package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Agreement;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.request.MyInfoRequest;
import com.whyranoid.walkie.dto.request.WalkieSignUpRequest;
import com.whyranoid.walkie.dto.response.MyInfoResponse;
import com.whyranoid.walkie.dto.response.WalkieSignUpResponse;
import com.whyranoid.walkie.repository.AgreementRepository;
import com.whyranoid.walkie.repository.WalkieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WalkieService {

    private final WalkieRepository walkieRepository;
    private final AgreementRepository agreementRepository;

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

        final Agreement agreement = agreementRepository.save(
                Agreement.builder()
                        .user(walkie)
                        .gpsService(walkieSignUpRequest.getAgreeGps())
                        .subscription(walkieSignUpRequest.getAgreeSubscription())
                        .build()
        );

        return WalkieSignUpResponse.builder()
                .hasDuplicatedName(false)
                .walkieId(walkie.getUserId())
                .nickname(walkie.getUserName())
                .build();
    }

    public boolean checkNameDuplication(String preName) {
        return walkieRepository.findByUserName(preName).isPresent();
    }

    public MyInfoResponse getMyInfo(Long walkieId) {
        if(walkieRepository.findByUserId(walkieId).isPresent()) {
            Walkie walkie = walkieRepository.findByUserId(walkieId).get();
            return MyInfoResponse.builder()
                    .profileImg(walkie.getProfileImg())
                    .nickname(walkie.getUserName())
                    .build();
        } else {
            return null;
        }
    }

    public MyInfoResponse changeMyInfo(Long walkieId, MyInfoRequest myInfoRequest) {
        Walkie walkie = walkieRepository.findByUserId(walkieId).orElseThrow();
        walkie.setProfileImg(myInfoRequest.getProfileImg());
        walkie.setUserName(myInfoRequest.getNickname());
        walkieRepository.save(walkie);
        return MyInfoResponse.builder()
                .profileImg(walkie.getProfileImg())
                .nickname(walkie.getUserName())
                .build();
    }
}
