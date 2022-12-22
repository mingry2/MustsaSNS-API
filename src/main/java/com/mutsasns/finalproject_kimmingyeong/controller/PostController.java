package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostCreateRequest;
import com.mutsasns.finalproject_kimmingyeong.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    // post 작성
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostCreateRequest postCreateRequest){
        log.info("title : {} body : {}", postCreateRequest.getTitle(), postCreateRequest.getBody());
        postService.create(postCreateRequest.getTitle(), postCreateRequest.getBody());
        return ResponseEntity.ok().body("포스트 등록 완료");
    }
}
