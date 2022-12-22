package com.mutsasns.finalproject_kimmingyeong.service;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;
import com.mutsasns.finalproject_kimmingyeong.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    // post 작성
    public PostCreateResponse create(String title, String body) {

        Post post = Post.builder()
                .title(title)
                .body(body)
                .build();
        postRepository.save(post);

        return PostCreateResponse.builder()
                .message("포스트 등록 완료")
                .postId(post.getId())
                .build();
    }
}
