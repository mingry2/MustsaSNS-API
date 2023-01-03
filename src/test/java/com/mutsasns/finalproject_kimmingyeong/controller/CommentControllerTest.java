package com.mutsasns.finalproject_kimmingyeong.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.CommentCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.user.join.UserJoinRequest;
import com.mutsasns.finalproject_kimmingyeong.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CommentService commentService;
    @Autowired
    ObjectMapper objectMapper;

    UserJoinRequest userJoinRequest;
    CommentCreateResponse commentCreateResponse;

    @BeforeEach
    void setUp(){
        userJoinRequest = new UserJoinRequest("mingyeong", "kmk1234");
        commentCreateResponse = new CommentCreateResponse(1L, "comment test", "mingyeong", 1L, LocalDateTime.now());

    }

    @Test
    @DisplayName("댓글 등록 완료")
    @WithMockUser
    void create() throws Exception {

        when(commentService.create(any(), any(), any()))
                .thenReturn(commentCreateResponse);

        mockMvc.perform(post("/api/v1/posts/1/comments")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result.commentId").value(1L))
                .andExpect(jsonPath("$.result.comment").value("comment test"))
                .andExpect(jsonPath("$.result.userName").value("mingyeong"))
                .andExpect(jsonPath("$.result.postId").value(1L));
    }

}