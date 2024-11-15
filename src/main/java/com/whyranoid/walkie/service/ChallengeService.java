package com.whyranoid.walkie.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.whyranoid.walkie.domain.*;
import com.whyranoid.walkie.dto.ChallengeDetailDto;
import com.whyranoid.walkie.dto.ChallengeDto;
import com.whyranoid.walkie.dto.request.ChallengeStatusChangeRequest;
import com.whyranoid.walkie.dto.request.ChallengeStatusCreateRequest;
import com.whyranoid.walkie.dto.response.ApiResponse;
import com.whyranoid.walkie.dto.response.BadgeDto;
import com.whyranoid.walkie.dto.response.ChallengePreviewDto;
import com.whyranoid.walkie.repository.BadgeRepository;
import com.whyranoid.walkie.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeService{

    private final ChallengeRepository challengeRepository;
    private final CommunityService communityService;
    private final BadgeRepository badgeRepository;

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
        String challengeSdate = challengeStatusCreateRequest.getChallengeSdate();

        Challenge challenge = challengeRepository.getChallengeById(challengeId);
        Walkie walkie = challengeRepository.getWalkieById(walkieId);

        ChallengeDetailDto challengeDetailDto = getChallengeDetail(challengeId, walkieId);
        if (challengeDetailDto.getChallenge().getStatus() != null) {
            return ApiResponse.builder()
                    .status(200)
                    .message("이미 시작한 챌린지입니다.")
                    .build();
        }

        ChallengeStatus cs = new ChallengeStatus();
        cs.setWalkie(walkie);
        cs.setChallenge(challenge);
        cs.setStatus('P');
        cs.setChallengeSdate(challengeSdate);

        challengeRepository.insertChallengeStatus(cs);

        return ApiResponse.builder()
                .status(200)
                .message("챌린지가 시작되었습니다.")
                .build();

    }

    public ApiResponse updateChallengeStatus(ChallengeStatusChangeRequest challengeStatusChangeRequest) {
            Character requestStatus = challengeStatusChangeRequest.getStatus();
            Long requestWalkieId = challengeStatusChangeRequest.getWalkieId();
            Long requestChallengeId = challengeStatusChangeRequest.getChallengeId();

            // 챌린지 중도 포기해서 삭제하려는 경우
            if(requestStatus == 'N') {
                challengeRepository.deleteChallengeStatus(requestWalkieId, requestChallengeId);
            }
            // 챌린지 현황 업데이트(완료 혹은 진행)
            else {
                try {
                    challengeRepository.updateChallengeStatus(requestWalkieId, requestChallengeId, challengeStatusChangeRequest);
                } catch (ParseException e) {
                    return ApiResponse.builder()
                            .status(500)
                            .message("시간 포맷 에러")
                            .build();
                }
            }

            return ApiResponse.builder()
                    .status(200)
                    .message("성공")
                    .build();
    }

    public ApiResponse adminUpdateChallenge(
            Character category,
            String startTime,
            String endTime,
            String content,
            MultipartFile challengeImg,
            String challengeName,
            Integer newFlag,
            Integer period,
            Long badgeId,
            Integer calorie,
            Integer distance,
            Integer goalCount,
            Integer timeLimit
    ) throws IOException, FirebaseAuthException {

        String challengeImgUrl = communityService.uploadCategorizedImg(challengeImg, "challenge");
        Badge badge = badgeRepository.getBadgeInfo(badgeId);

        Challenge challenge = Challenge.builder()
                .category(category)
                .badge(badge)
                .content(content)
                .name(challengeName)
                .img(challengeImgUrl)
                .period(period)
                .startTime(startTime)
                .endTime(endTime)
                .calorie(calorie)
                .distance(distance)
                .goalCount(goalCount)
                .timeLimit(timeLimit)
                .newFlag(newFlag)
                .build();

        challengeRepository.adminUpdateChallenge(challenge);

        return ApiResponse.builder()
                .status(200)
                .message("성공")
                .build();
    }


}
