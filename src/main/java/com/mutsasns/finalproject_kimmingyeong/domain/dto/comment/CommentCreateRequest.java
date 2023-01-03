package com.mutsasns.finalproject_kimmingyeong.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class CommentCreateRequest {
    private String comment;

}
