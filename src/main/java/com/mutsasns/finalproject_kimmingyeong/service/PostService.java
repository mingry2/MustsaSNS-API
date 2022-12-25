package com.mutsasns.finalproject_kimmingyeong.service;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostCreateResponse;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // post 작성
    public PostCreateResponse create(String title, String body, String userName) {
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

        return PostCreateResponse.builder()
                .message("포스트 등록 완료")
                .postId(post.getPostId())
                .build();

    }

    // post 전체 list 보기
    public Page<PostListResponse> getAll(Pageable pageable){
        Page<Post> postList = postRepository.findAll(pageable);
        Page<PostListResponse> postListResponses = PostListResponse.toResponse(postList);
        return postListResponses;
    }

    public PostListResponse getPost(Long postId) {

        // postId를 가진 포스트가 없을 경우
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
}
