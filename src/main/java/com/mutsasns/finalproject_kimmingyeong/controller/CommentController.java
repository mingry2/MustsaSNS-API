package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.CommentListResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.CommentCreateRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.CommentCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.response.Response;
import com.mutsasns.finalproject_kimmingyeong.service.CommentService;
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

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
@Data
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/{postsId}/comments")
    public Response<CommentCreateResponse> createComment(@PathVariable Long postsId, @RequestBody CommentCreateRequest commentCreateRequest, Authentication authentication){
        log.debug("postId : {} comment : {} userName : {}", postsId, commentCreateRequest, authentication.getName());
        CommentCreateResponse commentCreateResponse = commentService.create(postsId, commentCreateRequest, authentication.getName());
        return Response.success(commentCreateResponse);
    }

    // 댓글 조회
    @GetMapping("/{postId}/comments")
    public Response<Page<CommentListResponse>> listComment(@PathVariable Long postId, @PageableDefault(size = 10) @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable){
        Page<CommentListResponse> list = commentService.getAll(postId, pageable);
        return Response.success(list);
    }
}
