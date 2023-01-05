package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.create.CommentCreateRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.create.CommentCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.delete.CommentDeleteResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.list.CommentListResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.modify.CommentModifyRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.modify.CommentModifyResponse;
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

    // 댓글 수정
    @PutMapping("/{postId}/comments/{id}")
    public Response<CommentModifyResponse> modifyComment(@PathVariable Long postId, @PathVariable Long id, @RequestBody CommentModifyRequest commentModifyRequest, Authentication authentication){
        log.debug("postsId : {} id : {} commentModifyRequest.getComment : {} authentication.getName : {}", postId, id, commentModifyRequest.getComment(), authentication.getName());
        CommentModifyResponse commentModifyResponse = commentService.modify(postId, id, commentModifyRequest, authentication.getName());
        return Response.success(commentModifyResponse);
    }

    // 댓글 삭제
    @DeleteMapping("/{postsId}/comments/{id}")
    public Response<CommentDeleteResponse> deleteComment(@PathVariable Long postsId, @PathVariable Long id, Authentication authentication){
        log.debug("postsId : {} id : {} authentication.getName : {}", postsId, id, authentication.getName());
        CommentDeleteResponse commentDeleteResponse = commentService.delete(postsId, id, authentication.getName());
        return Response.success(commentDeleteResponse);
    }
}
