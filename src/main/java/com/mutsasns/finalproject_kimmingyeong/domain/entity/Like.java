package com.mutsasns.finalproject_kimmingyeong.domain.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Where(clause = "deleted_at is NULL")
@SQLDelete(sql = "UPDATE likes SET deleted_at = current_timestamp WHERE like_id = ?")
public class Like extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public static Like addLike(User user, Post post){
        return Like.builder()
                .user(user)
                .post(post)
                .build();
    }
}