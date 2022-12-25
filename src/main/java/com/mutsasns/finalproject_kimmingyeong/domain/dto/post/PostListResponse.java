package com.mutsasns.finalproject_kimmingyeong.domain.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
@Data
@Slf4j
public class PostListResponse {
    private Long id;
    private String title;
    private String body;
    private String userName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModifiedAt;


    // Post -> PostListResponse
    public static Page<PostListResponse> toResponse(Page<Post> postList) {
//        log.debug("User name in post entity: {}", post.getUser().getUserName());
        Page<PostListResponse> postListResponses = postList.map(post -> PostListResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .userName(post.getUser().getUserName())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build());
        return postListResponses;
    }

}
