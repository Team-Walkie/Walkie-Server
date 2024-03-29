package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Agreement;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.PostDto;
import com.whyranoid.walkie.dto.request.MyInfoRequest;
import com.whyranoid.walkie.dto.request.WalkieSignUpRequest;
import com.whyranoid.walkie.dto.response.MyInfoResponse;
import com.whyranoid.walkie.dto.response.WalkieSignUpResponse;
import com.whyranoid.walkie.repository.AgreementRepository;
import com.whyranoid.walkie.repository.PostRepository;
import com.whyranoid.walkie.repository.WalkieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WalkieService {

    private final WalkieRepository walkieRepository;
    private final AgreementRepository agreementRepository;
    private final PostRepository postRepository;

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

    public List<PostDto> getMyPostList(Long walkieId, Integer _pagingSize, Integer _pagingStart) {
        Walkie walkie = walkieRepository.findById(walkieId).orElseThrow(EntityNotFoundException::new);

        Integer pagingSize = _pagingSize == null ? 30 : _pagingSize;
        Integer pagingStart = _pagingStart == null ? 0 : _pagingStart;

        return postRepository.findMyPosts(walkieId, pagingSize, pagingStart);
    }
}
