package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Challenge;
import com.whyranoid.walkie.domain.ChallengeStatus;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.request.ChallengeStatusChangeRequest;
import com.whyranoid.walkie.dto.request.ChallengeStatusCreateRequest;
import com.whyranoid.walkie.dto.response.ApiResponse;
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

    public List<Challenge> getChallenges(Long userId, char challengeStatus) {
        return challengeRepository.getChallenges(userId, challengeStatus);
    }

    public List<ChallengeStatus> getChallengeDetail(Long challengeId, Long userId) {
        return challengeRepository.getDetailChallenge(challengeId, userId);
    }

    public ApiResponse createChallengeStatus(ChallengeStatusCreateRequest challengeStatusCreateRequest) {

        // walkie와 challenge는 클라이언트에서 보내줌, 근데 나는 userId랑 challengeId만 받고 싶은데..?
        Long userId = challengeStatusCreateRequest.getUserId();
        Long challengeId = challengeStatusCreateRequest.getChallengeId();

        Challenge challenge = challengeRepository.getChallengeById(challengeId);
        Walkie walkie = challengeRepository.getWalkieById(userId);

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
//        try {
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
//        }
//        catch(Exception e) {
//            return ApiResponse.builder()
//                    .status(400)
//                    .message("실패")
//                    .build();
//        }

    }
}
