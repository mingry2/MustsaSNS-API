package com.mutsasns.finalproject_kimmingyeong.domain.dto.comment;

import com.mutsasns.finalproject_kimmingyeong.domain.entity.Comment;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentCreateRequest {
    private String comment;

    public Comment toEntity(User user, Post post){
        return Comment.builder()
                .comment(this.comment)
                .user(user)
                .post(post)
                .build();
    }

}
