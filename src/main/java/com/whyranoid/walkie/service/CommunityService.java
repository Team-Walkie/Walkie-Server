package com.whyranoid.walkie.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.StorageClient;
import com.whyranoid.walkie.ApiBaseUrlSingleton;
import com.whyranoid.walkie.domain.Post;
import com.whyranoid.walkie.domain.PostLike;
import com.whyranoid.walkie.domain.Walkie;
import com.whyranoid.walkie.dto.PostDto;
import com.whyranoid.walkie.dto.PostLikeDto;
import com.whyranoid.walkie.dto.response.ApiResponse;
import com.whyranoid.walkie.repository.CommunityRepository;
import com.whyranoid.walkie.repository.FollowRepository;
import com.whyranoid.walkie.repository.PostLikeRepository;
import com.whyranoid.walkie.repository.PostRepository;
import com.whyranoid.walkie.repository.WalkieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final WalkieRepository walkieRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final FollowRepository followRepository;

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

    public PostLikeDto sendPostLike(PostLikeDto postLikeDto) {
        Post post = postRepository.findByPostId(postLikeDto.getPostId()).orElseThrow(EntityNotFoundException::new);
        Walkie liker = walkieRepository.findById(postLikeDto.getLikerId()).orElseThrow(EntityNotFoundException::new);

        PostLike input = PostLike.builder()
                .post(post)
                .liker(liker)
                .build();

        List<PostLike> already = new ArrayList<>(postLikeRepository.findByPostAndLiker(post, liker));
        if (!already.isEmpty()) {
            postLikeRepository.delete(already.get(0));
            postLikeDto.setLikerCount(-1L);
        }
        else {
            postLikeRepository.save(input);
            postLikeDto.setLikerCount(countPostLike(postLikeDto.getPostId()));
        }

        return postLikeDto;
    }

    public Long countPostLike(Long postId) {
        Post post = postRepository.findByPostId(postId).orElseThrow(EntityNotFoundException::new);

        return postLikeRepository.findPostLikeCount(postId);
    }

    public List<PostDto> getPostList(Long walkieId, Integer _pagingSize, Integer _pagingStart) {
        Walkie walkie = walkieRepository.findById(walkieId).orElseThrow(EntityNotFoundException::new);

        Integer pagingSize = _pagingSize == null ? 30 : _pagingSize;
        Integer pagingStart = _pagingStart == null ? 0 : _pagingStart;

        return postRepository.findCurrentPosts(followRepository.findFollowedIdList(walkieId), walkieId, pagingSize, pagingStart);
    }
}
