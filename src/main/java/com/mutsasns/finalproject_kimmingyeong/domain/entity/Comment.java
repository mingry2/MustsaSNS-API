package com.mutsasns.finalproject_kimmingyeong.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@javax.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
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

}
