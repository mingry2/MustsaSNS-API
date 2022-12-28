package com.mutsasns.finalproject_kimmingyeong.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class Post extends PostBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
