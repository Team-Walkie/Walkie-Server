package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Agreement;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.PostDto;
import com.whyranoid.walkie.dto.request.MyInfoRequest;
import com.whyranoid.walkie.dto.request.WalkieSignUpRequest;
import com.whyranoid.walkie.dto.response.MyInfoResponse;
import com.whyranoid.walkie.dto.response.WalkieLogInResponse;
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
	private final String baseImgUrl = "https://firebasestorage.googleapis.com/v0/b/walkie-5bfb3.appspot.com/o/profile%2Fbase.png?alt=media";

    public WalkieSignUpResponse joinWalkie(WalkieSignUpRequest walkieSignUpRequest) {
        if (checkNameDuplication(walkieSignUpRequest.getUserName())) {
            return WalkieSignUpResponse.builder()
                    .hasDuplicatedName(true)
                    .build();
        }
		
		String img = walkieSignUpRequest.getProfileImg();

        final Walkie walkie = walkieRepository.save(
                Walkie.builder()
                        .userName(walkieSignUpRequest.getUserName())
                        .name(walkieSignUpRequest.getName())
                        .profileImg((img == null || img.isBlank()) ? baseImgUrl : img)
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
                    .name(walkie.getName())
                    .build();
        } else {
            return null;
        }
    }

    public MyInfoResponse changeMyInfo(Long walkieId, MyInfoRequest myInfoRequest) {
        Walkie walkie = walkieRepository.findByUserId(walkieId).orElseThrow();

        String img = myInfoRequest.getProfileImg();
        walkie.setProfileImg((img == null || img.isBlank()) ? baseImgUrl : img);

        walkie.setUserName(myInfoRequest.getNickname());

        String name = myInfoRequest.getName();
        if (name != null && !name.isBlank()) {
            walkie.setName(name);
        }

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

    public Integer getMyPostCount(Long walkieId) {
        Walkie walkie = walkieRepository.findById(walkieId).orElseThrow(EntityNotFoundException::new);

        return postRepository.countMyPosts(walkieId);
    }

    public WalkieLogInResponse getWalkieId(String uid) throws EntityNotFoundException {
        return walkieRepository.findByAuthId(uid).map(WalkieLogInResponse::new).orElseThrow(EntityNotFoundException::new);
    }
}
