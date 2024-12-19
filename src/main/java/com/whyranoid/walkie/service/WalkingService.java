package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.History;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.domain.WalkingLike;
import com.whyranoid.walkie.dto.*;
import com.whyranoid.walkie.dto.request.ChallengeStatusChangeRequest;
import com.whyranoid.walkie.dto.response.ChallengePreviewDto;
import com.whyranoid.walkie.repository.HistoryRepository;
import com.whyranoid.walkie.repository.WalkieRepository;
import com.whyranoid.walkie.repository.WalkingLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class WalkingService {

    private final WalkieRepository walkieRepository;
    private final HistoryRepository historyRepository;
    private final WalkingLikeRepository walkingLikeRepository;

    private final ChallengeService challengeService;

    public Long startWalking(WalkingDto walkingDto) {
        Walkie user = walkieRepository.findById(walkingDto.getWalkieId()).orElseThrow(EntityNotFoundException::new);
        user.changeStatus('W');
        walkieRepository.save(user);

        Walkie walkie = walkieRepository.findById(walkingDto.getWalkieId()).orElseThrow(EntityNotFoundException::new);

        History input = History.builder()
                //.date(walkingDto.getStartTime())  // TODO 날짜 형식 반영
                .startTime(walkingDto.getStartTime())    // TODO 날짜 형식 반영
                .user(walkie)
                .build();

        History history = historyRepository.save(input);
        return history.getHistoryId();
    }

    public WalkingLikeDto sendWalkingLike(WalkingLikeDto request) {
        Walkie receiver = walkieRepository.findByUserIdAndStatus(request.getReceiverId(), 'W').orElseThrow(EntityNotFoundException::new);

        Walkie sender = walkieRepository.findById(request.getSenderId()).orElseThrow(EntityNotFoundException::new);

        History history = historyRepository.findFirst1ByUser(receiver, Sort.by("startTime").descending()).get(0);

        boolean already = !walkingLikeRepository.findByHistoryAndLiker(history, sender).isEmpty();
        if (already) return request;

        WalkingLike input = WalkingLike.builder()
                .history(history)
                .liker(sender)
                .build();

        walkingLikeRepository.save(input);
        return request;
    }

    public Long countWalkingLike(Long walkieId, String authId) {
        Walkie authWalkie = walkieRepository.findByAuthId(authId).orElseThrow(EntityNotFoundException::new);

        if (!authWalkie.getUserId().equals(walkieId)) throw new InvalidParameterException();

        return walkingLikeRepository.findWalkingLikeCount(authWalkie.getUserId());
    }

    public WalkingLikeDto getTotalWalkingLike(Long walkieId, String authId) {
        Walkie authWalkie = walkieRepository.findByAuthId(authId).orElseThrow(EntityNotFoundException::new);

        if (!authWalkie.getUserId().equals(walkieId)) throw new InvalidParameterException();

        return walkingLikeRepository.findWalkingLikePeople(walkieId);
    }

    public ChangedChallengeDto saveWalkingHistory(HistoryDto historyDto) throws ParseException {
        Walkie walkie = walkieRepository.findById(historyDto.getWalkieId()).orElseThrow(EntityNotFoundException::new);

        Long historyId = walkingLikeRepository.updateCurrentWalkingHistory(historyDto);
        History history = historyRepository.findById(historyId).orElseThrow(EntityNotFoundException::new);

        // 히스토리 업데이트
        walkingLikeRepository.updateCurrentWalkingHistory(historyDto);

        if (historyDto.getCalorie() != null) history.setCalorie(historyDto.getCalorie());
        if (historyDto.getDistance() != null) history.setDistance(historyDto.getDistance());
        if (historyDto.getStep() != null) history.setStep(history.getStep());
        history.setEndTime(history.getEndTime());
        history.setTotalTime(history.getTotalTime());

        historyRepository.save(history);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        Calendar startTimeCalendar = Calendar.getInstance();
        startTimeCalendar.setTime(format.parse(history.getStartTime()));
        String startTimeStr = String.valueOf(startTimeCalendar.get(Calendar.HOUR_OF_DAY))
                                + String.valueOf(startTimeCalendar.get(Calendar.MINUTE));

        // 도전중인 챌린지 업데이트
        List<ChallengePreviewDto> challengeList = challengeService.getProgressChallenges(walkie.getUserId());
        List<ChallengePreviewDto> failedChallenges = new ArrayList<>();
        List<ChallengePreviewDto> completedChallenges = new ArrayList<>();
        for(ChallengePreviewDto cp : challengeList) {

            // 챌린지 도전현황 정보 가져오기
            ChallengeDto curChallenge = challengeService.getChallengeDto(cp.getChallengeId(), walkie.getUserId());

            Character newStatus = curChallenge.getStatus();
            int accCount = 1;

            // 인정 운동시간 이상인지 판단
            if (Optional.ofNullable(cp.getPeriod()).orElse(0) > historyDto.getTotalTime() / 60) {
                accCount = 0;
            }
            // 달성 인정 시간대 계산(시작시간만 기준 -- 24.12.19)
            else if (curChallenge.getStartTime() != null
                        && curChallenge.getEndTime() != null
                        && startTimeStr.compareTo(curChallenge.getStartTime()) >= 0
                        && startTimeStr.compareTo(curChallenge.getEndTime()) <= 0) {
                accCount = 0;
            }

            Date prev = format.parse(curChallenge.getChallengeSdate());
            Date post = format.parse(history.getStartTime());
            Calendar prevCalendar = Calendar.getInstance();
            prevCalendar.setTime(prev);
            prevCalendar.add(Calendar.DAY_OF_MONTH, curChallenge.getTimeLimit());

            Integer leftDays = (int) (((prevCalendar.getTime().getTime() - post.getTime()) / (24 * 60 * 60 * 1000L)) % 365);

            // 운동 시작 시점에 이미 챌린지 제한기간이 지났는지 확인
            if (prevCalendar.getTime().before(post)) {
                newStatus = 'N';
            }
            // 이미 제한기간 안에 달성 횟수를 채울 수 없는 상태인지 확인
            else if (leftDays * Optional.ofNullable(curChallenge.getLimitPerDay()).orElse(999)
                    < (Optional.ofNullable(curChallenge.getGoalCount()).orElse(1) - Optional.ofNullable(curChallenge.getAccCount()).orElse(0) - 1)) {
                newStatus = 'N';
            }

            // progress와 완료여부 계산
            int newProgress = 0;
            if (accCount == 1 && newStatus != 'N') {
                if (curChallenge.getCategory() == 'C') {
                    newProgress = (Optional.ofNullable(history.getCalorie()).orElse(0)
                                    + Optional.ofNullable(curChallenge.getAccCalories()).orElse(0.0).intValue()) * 100
                            / curChallenge.getCalorie();
                }
                else if (curChallenge.getCategory() == 'D') {
                    newProgress = (Optional.ofNullable(history.getDistance()).orElse(0.0).intValue()
                            + Optional.ofNullable(curChallenge.getAccDistance()).orElse(0.0).intValue()) * 100
                            / curChallenge.getDistance();
                }
                else if (curChallenge.getCategory() == 'L') {
                    if (curChallenge.getGoalCount() != null) {
                        newProgress = (Optional.ofNullable(curChallenge.getAccCount()).orElse(0) + 1) * 100
                                / curChallenge.getGoalCount();
                    }
                    else {
                        newProgress = (Optional.ofNullable(history.getTotalTime()).orElse(0)
                                + getTimeSecFromString(Optional.ofNullable(curChallenge.getAccTime()).orElse("00:00:00"))) * 100
                                / curChallenge.getTime();
                    }
                }

                if (newProgress >= 100) {
                    newProgress = 100;
                    newStatus = 'C';
                }
            }

            if (newStatus == 'N') {
                failedChallenges.add(cp);
            }
            else if (newStatus == 'C') {
                completedChallenges.add(cp);
            }

            ChallengeStatusChangeRequest req = ChallengeStatusChangeRequest.builder()
                    .walkieId(walkie.getUserId())
                    .challengeId(cp.getChallengeId())
                    .progress(newProgress)
                    .status(newStatus)
                    .accDistance(historyDto.getDistance())
                    .accTime(getTimeStringFromSec(historyDto.getTotalTime()))
                    .accCalories(historyDto.getCalorie().doubleValue())
                    .accCount(accCount)
                    .build();

            challengeService.updateChallengeStatus(req);
        }

        return ChangedChallengeDto.builder()
                .failedChallenges(failedChallenges)
                .completedChallenges(completedChallenges)
                .ongoingChallenges(challengeService.getProgressChallenges(walkie.getUserId()))
                .build();
    }

    public String getTimeStringFromSec(Integer time) {
        Integer sec = time % 60;
        Integer min = (time / 60) % 60;
        Integer hour = (time / 60) / 60;

        return String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
    }

    public int getTimeSecFromString(String time) {
        String[] splits = time.split(":");
        return Integer.getInteger(splits[0]) * 3600
                + Integer.getInteger(splits[1]) * 60
                + Integer.getInteger(splits[2]);
    }
}
