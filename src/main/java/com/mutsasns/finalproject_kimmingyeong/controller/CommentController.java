package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.CommentCreateRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.CommentCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.response.Response;
import com.mutsasns.finalproject_kimmingyeong.service.CommentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
