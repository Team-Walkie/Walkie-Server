package com.whyranoid.walkie.service;

import com.whyranoid.walkie.domain.Post;
import com.whyranoid.walkie.dto.request.PostRequest;
import com.whyranoid.walkie.dto.response.ApiResponse;
import com.whyranoid.walkie.repository.CommunityRepository;
import com.whyranoid.walkie.repository.WalkieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final WalkieRepository walkieRepository;

    public ApiResponse uploadPost(PostRequest postRequest) {
        Post post = new Post();
        Long walkieId = postRequest.getWalkieId();
        String photo = postRequest.getPhoto();
        String content = postRequest.getContent();
        Integer colorMode = postRequest.getColorMode();
        Integer historyFlag = postRequest.getHistoryFlag();
        post.setPhoto(photo);
        post.setContent(content);
        post.setDate(LocalDateTime.now().toString());
        post.setColorMode(colorMode);
        post.setHistoryFlag(historyFlag);
        // 어떤 에러 던져야 할 지 논의해봐야 할 듯
        post.setUser(walkieRepository.findByUserId(walkieId).orElseThrow());
        communityRepository.uploadPost(post);
        return ApiResponse.builder()
                .status(200)
                .message("게시글 업로드 완료!")
                .build();
    }
}
