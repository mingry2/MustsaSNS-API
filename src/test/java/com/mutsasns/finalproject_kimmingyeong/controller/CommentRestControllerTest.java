package com.mutsasns.finalproject_kimmingyeong.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.create.CommentCreateRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.create.CommentCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.delete.CommentDeleteResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.modify.CommentModifyRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.modify.CommentModifyResponse;
import com.mutsasns.finalproject_kimmingyeong.exception.AppException;
import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import com.mutsasns.finalproject_kimmingyeong.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentRestController.class)
class CommentRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CommentService commentService;
    @Autowired
    ObjectMapper objectMapper;

    CommentCreateResponse commentCreateResponse;
    CommentCreateRequest commentCreateRequest;
    CommentModifyResponse commentModifyResponse;
    CommentModifyRequest commentModifyRequest;
    CommentDeleteResponse commentDeleteResponse;

    @BeforeEach
    void setUp() {
        commentCreateRequest = new CommentCreateRequest("comment test");
        commentModifyRequest = new CommentModifyRequest("comment modify test");
        commentCreateResponse = new CommentCreateResponse(1L, "comment test", "mingyeong", 1L, LocalDateTime.now());
        commentModifyResponse = new CommentModifyResponse(1L, "comment modify test", "mingyeong", 1L, LocalDateTime.now(), LocalDateTime.now());
        commentDeleteResponse = new CommentDeleteResponse("????????? ?????? ??????", 1L);
    }

    @Nested
    class comment_create {
        @Test
        @DisplayName("?????? ?????? ??????")
        @WithMockUser
        void create() throws Exception {

            when(commentService.create(any(), any(), any()))
                    .thenReturn(commentCreateResponse);
//                .thenReturn(mock(CommentCreateResponse.class));

            mockMvc.perform(post("/api/v1/posts/1/comments")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(commentCreateRequest)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                    .andExpect(jsonPath("$.result.id").value(1L))
                    .andExpect(jsonPath("$.result.comment").value("comment test"))
                    .andExpect(jsonPath("$.result.userName").value("mingyeong"))
                    .andExpect(jsonPath("$.result.postId").value(1L));
        }

        @Test
        @DisplayName("?????? ?????? ??????(1) - ??????????????? ?????? ??????")
        @WithMockUser
        void create_fail1() throws Exception {

            when(commentService.create(any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

            mockMvc.perform(post("/api/v1/posts/1/comments")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(commentCreateRequest)))
                    .andDo(print())
                    .andExpect(status().is(ErrorCode.USERNAME_NOT_FOUND.getHttpStatus().value()))
                    .andExpect(jsonPath("$.resultCode").value("ERROR"))
                    .andExpect(jsonPath("$.result.message").value("Not founded"));
        }

        @Test
        @DisplayName("?????? ?????? ??????(2) - ???????????? ???????????? ?????? ??????")
        @WithMockUser
        void create_fail2() throws Exception {

            when(commentService.create(any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

            mockMvc.perform(post("/api/v1/posts/1/comments")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(commentCreateRequest)))
                    .andDo(print())
                    .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getHttpStatus().value()))
                    .andExpect(jsonPath("$.resultCode").value("ERROR"))
                    .andExpect(jsonPath("$.result.message").value("?????? ???????????? ????????????."));
        }

    }

    @Nested
    class comment_modify {
        @Test
        @DisplayName("?????? ?????? ??????")
        @WithMockUser
        void modify() throws Exception {

            when(commentService.modify(any(), any(), any(), any()))
                    .thenReturn(commentModifyResponse);

            mockMvc.perform(put("/api/v1/posts/1/comments/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(commentModifyRequest)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                    .andExpect(jsonPath("$.result.id").value(1L))
                    .andExpect(jsonPath("$.result.comment").value("comment modify test"))
                    .andExpect(jsonPath("$.result.userName").value("mingyeong"))
                    .andExpect(jsonPath("$.result.postId").value(1L));
        }

        @Test
        @DisplayName("?????? ?????? ??????(1) - ?????? ??????")
        @WithAnonymousUser
        void modify_fail1() throws Exception {

            when(commentService.modify(any(), any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

            mockMvc.perform(post("/api/v1/posts/1/comments/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(commentModifyRequest)))
                    .andDo(print())
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("?????? ?????? ??????(2) - Post ?????? ??????")
        @WithMockUser
        void post_modify_fail2() throws Exception {

            when(commentService.modify(any(), any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

            mockMvc.perform(put("/api/v1/posts/1/comments/1")
                            .with(csrf())
                            // json ???????????? ??????
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(commentModifyRequest)))
                    .andDo(print())
                    .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getHttpStatus().value()));
        }

        @Test
        @DisplayName("?????? ?????? ??????(3) - ????????? ?????????")
        @WithMockUser
        void post_modify_fail3() throws Exception {

            when(commentService.modify(any(), any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

            mockMvc.perform(put("/api/v1/posts/1/comments/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(commentModifyRequest)))
                    .andDo(print())
                    .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
        }

        @Test
        @DisplayName("?????? ?????? ??????(4) - ?????????????????? ??????")
        @WithMockUser
        void post_modify_fail4() throws Exception {

            when(commentService.modify(any(), any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.DATABASE_ERROR, ErrorCode.DATABASE_ERROR.getMessage()));

            mockMvc.perform(put("/api/v1/posts/1/comments/1")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(commentModifyRequest)))
                    .andDo(print())
                    .andExpect(status().is(ErrorCode.DATABASE_ERROR.getHttpStatus().value()));
        }

    }

    @Nested
    class comment_deleted {
        @Test
        @DisplayName("?????? ?????? ??????")
        @WithMockUser
        void deleted() throws Exception {

            when(commentService.delete(any(), any(), any()))
                    .thenReturn(commentDeleteResponse);

            mockMvc.perform(delete("/api/v1/posts/1/comments/1")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                    .andExpect(jsonPath("$.result.message").value("????????? ?????? ??????"))
                    .andExpect(jsonPath("$.result.id").value(1L));
        }

        @Test
        @DisplayName("?????? ?????? ??????(1) - ?????? ??????")
        @WithAnonymousUser
        void post_delete_fail1() throws Exception {

            when(commentService.delete(any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

            mockMvc.perform(delete("/api/v1/posts/1/comments/1")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("?????? ?????? ??????(2) - Post ?????? ??????")
        @WithMockUser
        void post_delete_fail2() throws Exception {

            when(commentService.delete(any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

            mockMvc.perform(delete("/api/v1/posts/1/comments/1")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getHttpStatus().value()));
        }

        @Test
        @DisplayName("?????? ?????? ??????(3) - ????????? ?????????")
        @WithMockUser
        void post_delete_fail3() throws Exception {

            when(commentService.delete(any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

            mockMvc.perform(delete("/api/v1/posts/1/comments/1")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
        }

        @Test
        @DisplayName("?????? ?????? ??????(4) - ?????????????????? ??????")
        @WithMockUser
        void post_delete_fail4() throws Exception {

            when(commentService.delete(any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.DATABASE_ERROR, ErrorCode.DATABASE_ERROR.getMessage()));

            mockMvc.perform(delete("/api/v1/posts/1/comments/1")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().is(ErrorCode.DATABASE_ERROR.getHttpStatus().value()));
        }
    }

}


