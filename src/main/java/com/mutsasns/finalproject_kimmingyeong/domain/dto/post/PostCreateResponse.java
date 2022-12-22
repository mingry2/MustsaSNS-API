package com.mutsasns.finalproject_kimmingyeong.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostCreateResponse {
    private String message;
    private Long postId;

}
