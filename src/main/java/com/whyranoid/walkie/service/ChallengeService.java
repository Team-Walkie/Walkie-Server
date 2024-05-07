package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.ChallengeDetailDto;
import com.whyranoid.walkie.dto.ChallengeDto;
import com.whyranoid.walkie.dto.request.ChallengeStatusChangeRequest;
import com.whyranoid.walkie.dto.request.ChallengeStatusCreateRequest;
import com.whyranoid.walkie.dto.response.ApiResponse;
import com.whyranoid.walkie.dto.response.ChallengePreviewDto;
import com.whyranoid.walkie.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeService{

    private final ChallengeRepository challengeRepository;

    public List<ChallengePreviewDto> getNewChallenges(Long walkieId) {
        return challengeRepository.getNewChallenges(walkieId);
    }

    public List<ChallengePreviewDto> getPopularChallenges() {
        return challengeRepository.getPopularChallenges();
    }

    public List<ChallengePreviewDto> getChallengesByCategory(Long walkieId, char category) {
        return challengeRepository.getChallengesByCategory(walkieId, category);
    }

    public List<ChallengePreviewDto> getProgressChallenges(Long walkieId) {
        return challengeRepository.getProgressChallenges(walkieId);
    }

    public ChallengeDetailDto getChallengeDetail(Long challengeId, Long walkieId) {
        Object cs = challengeRepository.getChallengeDetail(challengeId, walkieId);
        List<Walkie> walkies = challengeRepository.getChallengeMember(challengeId, walkieId);

        if(cs == null) {
            return null;
        } else {
            return ChallengeDetailDto.builder()
                    .challenge((ChallengeDto)cs)
                    .walkies(walkies)
                    .build();
        }
    }

    public ChallengeDetailDto getChallengeOnlyDetail(Long challengeId, Long walkieId) {
        Challenge challenge = challengeRepository.getChallengeById(challengeId);
        List<Walkie> walkies = challengeRepository.getChallengeMember(challengeId, walkieId);

        return ChallengeDetailDto.builder()
                .challenge(new ChallengeDto(challenge))
                .walkies(walkies)
                .build();
    }

    public ApiResponse createChallengeStatus(ChallengeStatusCreateRequest challengeStatusCreateRequest) {

        // walkie와 challenge는 클라이언트에서 보내줌, 근데 나는 walkieId랑 challengeId만 받고 싶은데..?
        Long walkieId = challengeStatusCreateRequest.getWalkieId();
        Long challengeId = challengeStatusCreateRequest.getChallengeId();

        Challenge challenge = challengeRepository.getChallengeById(challengeId);
        Walkie walkie = challengeRepository.getWalkieById(walkieId);

        ChallengeStatus cs = new ChallengeStatus();
        cs.setWalkie(walkie);
        cs.setChallenge(challenge);
        cs.setStatus('P');

        challengeRepository.insertChallengeStatus(cs);

        return ApiResponse.builder()
                .status(200)
                .message("챌린지가 시작되었습니다.")
                .build();

    }

    public ApiResponse updateChallengeStatus(ChallengeStatusChangeRequest challengeStatusChangeRequest) {
            Character requestStatus = challengeStatusChangeRequest.getStatus();
            Long requestStatusId = challengeStatusChangeRequest.getStatusId();
            // 챌린지 중도 포기해서 삭제하려는 경우
            if(requestStatus == 'N') {
                challengeRepository.deleteChallengeStatus(requestStatusId);
            }
            // 챌린지 조건에 도달해서 완료로 표시해야 하는 경우
            else if(requestStatus == 'C') {
                challengeRepository.updateChallengeStatus(requestStatusId, requestStatus);
            }

            return ApiResponse.builder()
                    .status(200)
                    .message("성공")
                    .build();
    }
}
