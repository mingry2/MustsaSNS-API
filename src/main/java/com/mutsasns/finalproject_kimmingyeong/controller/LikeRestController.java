package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.like.LikeAddResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.response.Response;
import com.mutsasns.finalproject_kimmingyeong.service.LikeService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(
            value = "좋아요 누르기"
            , notes = "like 한 번 누르면 1, 한 번 더 누르면 0(soft deletedAt 적용) -> 중복 불가")
    @PostMapping("/{postId}/likes")
    public Response<LikeAddResponse> pushLike(@PathVariable Long postId, Authentication authentication){
        likeService.addLike(postId, authentication.getName());
        return Response.success(LikeAddResponse.builder()
                                            .message("좋아요를 눌렀습니다.")
                                            .build());

    }

    // 좋아요 개수
    @ApiOperation(
            value = "좋아요 조회"
            , notes = "포스트 좋아요 개수 조회")
    @GetMapping("/{postId}/likes")
    public Response<Long> countLike(@PathVariable Long postId, Authentication authentication){
        Long likeCount = likeService.countLike(postId, authentication.getName());
        return Response.success(likeCount);

    }
}
