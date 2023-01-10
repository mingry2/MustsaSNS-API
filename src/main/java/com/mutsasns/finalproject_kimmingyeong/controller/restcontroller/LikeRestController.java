package com.mutsasns.finalproject_kimmingyeong.controller.restcontroller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.like.LikeAddResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.response.Response;
import com.mutsasns.finalproject_kimmingyeong.service.LikeService;
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
public class LikeRestController {

    private final LikeService likeService;

    // 좋아요 누르기
    @PostMapping("/{postId}/likes")
    public Response<LikeAddResponse> pushLike(@PathVariable Long postId, Authentication authentication){
        likeService.addLike(postId, authentication.getName());
        return Response.success(LikeAddResponse.builder()
                                            .message("좋아요를 눌렀습니다.")
                                            .build());

    }

    // 좋아요 개수
    @GetMapping("/{postId}/likes")
    public Response<Long> countLike(@PathVariable Long postId, Authentication authentication){
        Long likeCount = likeService.countLike(postId, authentication.getName());
        return Response.success(likeCount);

    }
}
