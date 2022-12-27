package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostDeletedResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.*;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.response.Response;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;
import com.mutsasns.finalproject_kimmingyeong.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.sun.tools.attach.VirtualMachine.list;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
@Data
public class PostController {

    private final PostService postService;

    // post 작성
    @PostMapping("")
    public Response<PostCreateResponse> createPost(@RequestBody PostCreateRequest postCreateRequest, Authentication authentication){
        log.info("title : {} body : {}", postCreateRequest.getTitle(), postCreateRequest.getBody());
        PostCreateResponse postCreateResponse = postService.create(postCreateRequest.getTitle(), postCreateRequest.getBody(), authentication.getName());
        return Response.success(postCreateResponse);
    }

    // post 전체 list 보기
    @GetMapping("")
    public Response<Page<PostListResponse>> postList(@PageableDefault(size = 20) @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable){
        Page<PostListResponse> list = postService.getAll(pageable);
        log.info("list : {} ", list());
        return Response.success(list);
    }

    // postId로 조회하여 해당 id의 post 조회하기
    @GetMapping("/{postId}")
    public Response<PostListResponse> findById(@PathVariable Long postId){
        log.debug("postId : {} ", postId);
        PostListResponse postListResponse = postService.getPost(postId);
        log.debug("postListResponse : {} ", postListResponse);
        return Response.success(postListResponse);
    }

    // postId로 조회한 포스트 수정하기
    @PutMapping("/{postId}")
    public Response<PostModifiedResponse> modify(@PathVariable Long postId, @RequestBody PostModifyRequest postModifyRequest, Authentication authentication){
        log.debug("userName : {} postId : {} title : {} body : {} ", authentication.getName(), postId, postModifyRequest.getTitle(), postModifyRequest.getBody());
        Post post = postService.modify(authentication.getName(), postId, postModifyRequest.getTitle(), postModifyRequest.getBody());
        PostModifiedResponse postModifiedResponse = PostModifiedResponse.builder()
                .message("포스트 수정 완료")
                .postId(post.getPostId())
                .build();
        return Response.success(postModifiedResponse);
    }

    // postId로 조회한 포스트 삭제하기
    @DeleteMapping("/{postId}")
    public Response<PostDeletedResponse> delete(@PathVariable Long postId, Authentication authentication){
        postService.delete(postId, authentication.getName());
        PostDeletedResponse postDeletedResponse = PostDeletedResponse.builder()
                .message("포스트 삭제 완료")
                .postId(postId)
                .build();
        return Response.success(postDeletedResponse);
    }
}
