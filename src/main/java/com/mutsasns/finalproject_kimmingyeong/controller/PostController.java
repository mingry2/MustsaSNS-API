package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostCreateRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostListResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.response.Response;
import com.mutsasns.finalproject_kimmingyeong.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;


    // post 작성
    @PostMapping("")
    public Response<PostCreateResponse> createPost(@RequestBody PostCreateRequest postCreateRequest, Authentication authentication){
        log.info("title : {} body : {}", postCreateRequest.getTitle(), postCreateRequest.getBody());
        PostCreateResponse postCreateResponse = postService.create(postCreateRequest.getTitle(), postCreateRequest.getBody(), authentication.getName());
        return Response.success(postCreateResponse);
    }

    // posts list
    @GetMapping("")
    public ResponseEntity<List<PostListResponse>> postList(){
        List<PostListResponse> list = postService.getAll();
        return ResponseEntity.ok().body(list);
    }
}
