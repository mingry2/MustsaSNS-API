package com.mutsasns.finalproject_kimmingyeong.controller.restcontroller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.*;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.response.Response;
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
public class PostRestController {

    private final PostService postService;

    // 포스트 작성
    @PostMapping("")
    public Response<PostResponse> createPost(@RequestBody PostRequest postRequest, Authentication authentication){
        log.info("title : {} body : {}", postRequest.getTitle(), postRequest.getBody());
        PostResponse postResponse = postService.create(postRequest.getTitle(), postRequest.getBody(), authentication.getName());
        return Response.success(postResponse);
    }

    // 포스트 전체 조회
    @GetMapping("")
    public Response<Page<PostListResponse>> postList(@PageableDefault(size = 20) @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable){
        Page<PostListResponse> list = postService.getAll(pageable);
        log.info("list : {} ", list());
        return Response.success(list);
    }

    // 포스트 1개 조회
    @GetMapping("/{postId}")
    public Response<PostListResponse> findById(@PathVariable Long postId){
        log.debug("postId : {} ", postId);
        PostListResponse postListResponse = postService.getPost(postId);
        log.debug("postListResponse : {} ", postListResponse);
        return Response.success(postListResponse);
    }

    // 포스트 수정
    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable Long postId, @RequestBody PostRequest postModifyRequest, Authentication authentication){
        log.debug("userName : {} postId : {} title : {} body : {} ", authentication.getName(), postId, postModifyRequest.getTitle(), postModifyRequest.getBody());
        PostResponse postModifiedResponse = postService.modify(authentication.getName(), postId, postModifyRequest.getTitle(), postModifyRequest.getBody());
        return Response.success(postModifiedResponse);
    }

    // 포스트 삭제
    @DeleteMapping("/{postId}")
    public Response<PostResponse> delete(@PathVariable Long postId, Authentication authentication){
        postService.delete(postId, authentication.getName());
        PostResponse postDeletedResponse = PostResponse.builder()
                .message("포스트 삭제 완료")
                .postId(postId)
                .build();
        return Response.success(postDeletedResponse);
    }

    // 마이피드 조회
    @GetMapping("/my")
    public Response<Page<PostListResponse>> myFeed(Authentication authentication, @PageableDefault(size = 20) @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable){
        Page<PostListResponse> myFeed = postService.myFeedAll(authentication.getName(), pageable);
        return Response.success(myFeed);

    }

}
