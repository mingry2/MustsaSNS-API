package com.mutsasns.finalproject_kimmingyeong.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentDeleteResponse {
    private String message;
    private Long id;

}
