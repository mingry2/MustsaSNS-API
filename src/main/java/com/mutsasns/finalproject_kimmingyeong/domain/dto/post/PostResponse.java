package com.mutsasns.finalproject_kimmingyeong.domain.dto.post;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class PostResponse {
    private String message;
    private Long postId;

}
