package com.mutsasns.finalproject_kimmingyeong.domain.dto.post;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class PostModifyRequest {
    private String title;
    private String body;
}
