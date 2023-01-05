package com.mutsasns.finalproject_kimmingyeong.service;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostListResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.User;
import com.mutsasns.finalproject_kimmingyeong.exception.AppException;
import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import com.mutsasns.finalproject_kimmingyeong.repository.PostRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 포스트 등록
    public PostResponse create(String title, String body, String userName) {
        // userName 없음
        log.info("userName : {} ", userName);
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // 포스트 등록 완료
        log.info("title : {} body : {} ", title, body);
        Post post = Post.builder()
                .title(title)
                .body(body)
                .user(user)
                .build();
        postRepository.save(post);

        return PostResponse.builder()
                .message("포스트 등록 완료")
                .postId(post.getPostId())
                .build();

    }

    // 포스트 전체 조회
    public Page<PostListResponse> getAll(Pageable pageable){
        Page<Post> postList = postRepository.findAll(pageable);
        Page<PostListResponse> postListResponses = PostListResponse.toResponse(postList);
        return postListResponses;
    }

    // 포스트 1개 조회
    public PostListResponse getPost(Long postId) {

        // postId 없는 경우
        Post findByIdPost = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // 포스트 조회 완료
        return PostListResponse.builder()
                .id(findByIdPost.getPostId())
                .title(findByIdPost.getTitle())
                .body(findByIdPost.getBody())
                .userName(findByIdPost.getUser().getUserName())
                .createdAt(findByIdPost.getCreatedAt())
                .lastModifiedAt(findByIdPost.getLastModifiedAt())
                .build();
    }

    // 포스트 수정
    public PostResponse modify(String userName, Long postId, String title, String body) {

        // postId 없는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // 해당 userName으로 작성한 포스트가 없을 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Long userId = user.getUserId();

        // User DB에 저장된 userId와 Post DB에 저장된 postId가 같지 않다면, 권한 없음 에러 처리 -> 같아야 userName이 동일한것
        if (!Objects.equals(post.getUser().getUserId(), userId)) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        // 수정 포스트 저장
        post.setTitle(title); // PostModifyRequest에서 수정한 title로 post에 저장
        post.setBody(body); // PostModifyRequest에서 수정한 body로 post에 저장
        Post savedPost = postRepository.saveAndFlush(post);

        return PostResponse.builder()
                .message("포스트 수정 완료")
                .postId(savedPost.getPostId())
                .build();
    }

    // 포스트 삭제
    public boolean delete(Long postId, String userName) {
        log.debug("postId: {} userName: {} ", postId, userName);

        // postId 없는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // 해당 userName으로 작성한 포스트가 없을 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Long userId = user.getUserId();
        log.debug("userId: {} ", userId);

        // User DB에 저장된 userId와 Post DB에 저장된 postId가 같지 않다면, 권한 없음 에러 처리 -> 같아야 userName이 동일한것
        if (!Objects.equals(post.getUser().getUserId(), userId)) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        postRepository.deleteById(postId);

        return true;
    }

    // 마이피드 조회
    public Page<PostListResponse> myFeedAll(String userName, Pageable pageable) {

        // 해당 userName으로 작성한 포스트가 없을 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // userName으로 조회되는 포스트 모두 조회
        Page<Post> userPosts = postRepository.findAllByUser(user, pageable);
        Page<PostListResponse> postListResponses = PostListResponse.toResponse(userPosts);

        return postListResponses;

    }
}
