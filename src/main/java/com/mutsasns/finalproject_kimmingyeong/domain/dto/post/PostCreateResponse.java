package com.mutsasns.finalproject_kimmingyeong.domain.dto.post;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class PostCreateResponse {
    private String message;
    private Long postId;

}
