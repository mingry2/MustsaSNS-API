package com.mutsasns.finalproject_kimmingyeong.domain.entity;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.CommentCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.CommentModifyResponse;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@javax.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public CommentCreateResponse toCreateResponse(){
        return CommentCreateResponse.builder()
                .commentId(this.getCommentId())
                .comment(this.getComment())
                .userName(this.getUser().getUserName())
                .postId(this.getPost().getPostId())
                .createAt(getCreatedAt())
                .build();
    }

    public CommentModifyResponse toModifyResponse(){
        return CommentModifyResponse.builder()
                .commentId(this.getCommentId())
                .comment(this.getComment())
                .userName(this.getUser().getUserName())
                .postId(this.getPost().getPostId())
                .createAt(getCreatedAt())
                .lastModifiedAt(getLastModifiedAt())
                .build();
    }

}
