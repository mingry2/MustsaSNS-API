package com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentCreateResponse {
    private Long commentId;
    private String comment;
    private String userName;
    private Long postId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;

}
