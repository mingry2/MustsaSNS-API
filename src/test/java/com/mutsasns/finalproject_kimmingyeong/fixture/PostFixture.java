package com.mutsasns.finalproject_kimmingyeong.fixture;

import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;

public class PostFixture {
    public static Post get(String userName, String password) {
        Post post = Post.builder()
                .postId(1L)
                .user(UserFixture.get(userName, password))
                .title("title")
                .body("body")
                .build();

        return post;
    }

}
