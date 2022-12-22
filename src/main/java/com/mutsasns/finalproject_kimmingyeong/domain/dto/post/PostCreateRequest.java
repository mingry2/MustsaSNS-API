package com.mutsasns.finalproject_kimmingyeong.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Data
public class PostCreateRequest {
    private String title;
    private String body;
}
