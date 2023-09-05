package com.whyranoid.walkie.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.StorageClient;
import com.whyranoid.walkie.ApiBaseUrlSingleton;
import com.whyranoid.walkie.domain.Post;
import com.whyranoid.walkie.dto.response.ApiResponse;
import com.whyranoid.walkie.repository.CommunityRepository;
import com.whyranoid.walkie.repository.WalkieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final WalkieRepository walkieRepository;

    @Value("${app.firebase-bucket-name}")
    private String firebaseBucket;

    public ApiResponse uploadPost(MultipartFile image, Long walkieId, String content, Integer colorMode, String historyContent) throws IOException, FirebaseAuthException {
        String imageUrl = "post/" + UUID.randomUUID() + ".jpg";
        Post post = new Post();
        post.setContent(content);
        post.setDate(LocalDateTime.now().toString());
        post.setColorMode(colorMode);
        post.setHistoryContent(historyContent);
        // 어떤 에러 던져야 할 지 논의해봐야 할 듯
        post.setUser(walkieRepository.findByUserId(walkieId).orElseThrow());
        uploadImage(image, imageUrl);
        post.setPhoto(ApiBaseUrlSingleton.getInstance().getBaseUrl()+ '/' + imageUrl);
        communityRepository.uploadPost(post);
        return ApiResponse.builder()
                .status(200)
                .message("게시글 업로드 완료!")
                .build();
    }

    public String uploadImage(MultipartFile image, String nameFile)
        throws IOException, FirebaseAuthException {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        InputStream content = new ByteArrayInputStream(image.getBytes());
        Blob blob = bucket.create(nameFile, content, image.getContentType());
        return blob.getMediaLink();
    }
}
