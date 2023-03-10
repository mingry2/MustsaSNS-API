package com.mutsasns.finalproject_kimmingyeong.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostListResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;
import com.mutsasns.finalproject_kimmingyeong.exception.AppException;
import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import com.mutsasns.finalproject_kimmingyeong.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostRestController.class)
class PostRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PostService postService;
    @Autowired
    ObjectMapper objectMapper;
    PostRequest postRequest = new PostRequest("test title", "test body"); // ??? ?????? ??? ????????? ????????? title, body
    PostRequest modifyRequest = new PostRequest("test modify title", "test modify body"); // ??? ?????? ??? ????????? ????????? modify title, modify body

    @Nested
    class post_create_test{
        @Test
        @DisplayName("????????? ?????? ??????")
        @WithMockUser
        void create() throws Exception {

            when(postService.create(any(), any(), any()))
                    .thenReturn(PostResponse.builder()
                            .message("????????? ?????? ??????")
                            .postId(1L)
                            .build());

            mockMvc.perform(post("/api/v1/posts")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(postRequest)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result.message").value("????????? ?????? ??????"))
                    .andExpect(jsonPath("$.result.postId").value(1L));
        }

        @Test
        @DisplayName("????????? ?????? ??????(1) - ????????????(JWT??? Bearer Token?????? ????????? ?????? ??????")
        @WithMockUser
        void create_fail1() throws Exception {

            when(postService.create(any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage()));

            mockMvc.perform(post("/api/v1/posts")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(postRequest)))
                    .andDo(print())
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.result.errorCode").value("INVALID_TOKEN"))
                    .andExpect(jsonPath("$.result.message").value("????????? ???????????????."));
        }

        @Test
        @DisplayName("????????? ?????? ??????(2) - ????????????(JWT??? ???????????? ?????? ??????)")
        @WithMockUser
        void create_fail2() throws Exception {

            when(postService.create(any(), any(), any()))
                    .thenThrow(new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage()));

            mockMvc.perform(post("/api/v1/posts")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(postRequest)))
                    .andDo(print())
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.result.errorCode").value("INVALID_TOKEN"))
                    .andExpect(jsonPath("$.result.message").value("????????? ???????????????."));
        }

    }

    @Nested
    class post_get_test{
        @Test
        @DisplayName("????????? 1??? ?????? ??????")
        @WithMockUser
        void post_get_success() throws Exception {

            PostListResponse postListResponse = PostListResponse.builder()
                    .id(2L)
                    .title("test title")
                    .body("test body")
                    .userName("test userName")
                    .createdAt(LocalDateTime.now())
                    .lastModifiedAt(LocalDateTime.now())
                    .build();

            when(postService.getPost(any()))
                    .thenReturn(postListResponse);

            mockMvc.perform(get("/api/v1/posts/2")
                            .with(csrf()))
                    // result ?????? ??????????????? ????????? .result??? ?????? ????????? ???????????????.
                    .andExpect(jsonPath("$.result.id").exists())
                    .andExpect(jsonPath("$.result.title").exists())
                    .andExpect(jsonPath("$.result.body").exists())
                    .andExpect(jsonPath("$.result.userName").exists())
                    .andExpect(jsonPath("$.result.createdAt").exists())
                    .andExpect(jsonPath("$.result.lastModifiedAt").exists())
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("????????? ?????? ?????? ?????? - pageable ??????")
        @WithMockUser
        void post_get_all_success() throws Exception {

            mockMvc.perform(get("/api/v1/posts")
                            .param("page", "0")
                            .param("size", "3")
                            .param("sort", "createdAt,desc"))
                    .andExpect(status().isOk());

            // ArgumentCaptor ?????? -> ????????? ????????? ??? ????????? ????????? ????????? ??? ??????
            // ????????? ?????? ????????? ???????????? ???????????? ?????? ????????? ??? ????????? ????????? ????????? ??? ??????
            // capture() ???????????? ?????? getValue() ???????????? ?????? ?????? ?????? ???????????? ??????
            ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
            verify(postService).getAll(pageableArgumentCaptor.capture());
            PageRequest pageRequest = (PageRequest) pageableArgumentCaptor.getValue();

            assertEquals(0, pageRequest.getPageNumber());
            assertEquals(3, pageRequest.getPageSize());
            assertEquals(Sort.by("createdAt", "desc"), pageRequest.withSort(Sort.by("createdAt","desc")).getSort());
        }

    }

    @Nested
    class post_modify_test{
        @Test
        @DisplayName("????????? ?????? ??????")
        @WithMockUser // ????????? ???????????? ??????????????? ????????? ??????
        void post_modify_success() throws Exception {

            // ?????? ??? postId
            Post post = Post.builder()
                    .postId(1L)
                    .build();

            // Post -> PostResponse
            PostResponse postModifyResponse = PostResponse.builder()
                    .message("????????? ?????? ??????")
                    .postId(post.getPostId())
                    .build();

            // ??????, postId, request.title, request.body
            when(postService.modify(any(),any(),any(),any()))
                    .thenReturn(postModifyResponse);

            mockMvc.perform(put("/api/v1/posts/1")
                            .with(csrf())
                            // json ???????????? ??????
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(modifyRequest)))
                    .andDo(print())
                    .andExpect(jsonPath("$.result.message").value("????????? ?????? ??????"))
                    .andExpect(jsonPath("$.result.postId").value(1L))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("????????? ?????? ??????(1) - ?????? ??????")
        @WithAnonymousUser // ???????????? ?????? ???????????? ??????????????? ????????? ??????
        void post_modify_fail1() throws Exception {

            // ??????, postId, title, body -> ??????.getName??? userName??? ?????? ??????
            when(postService.modify(any(),any(),any(),any()))
                    .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

            mockMvc.perform(put("/api/v1/posts/1")
                            .with(csrf())
                            // json ???????????? ??????
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(modifyRequest)))
                    .andDo(print())
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("????????? ?????? ??????(2) - ????????? ?????? ?????????")
        @WithMockUser
        void post_modify_fail2() throws Exception {

            // ??????, postId, title, body -> postId??? ???????????? ????????? ??????
            when(postService.modify(any(),any(),any(),any()))
                    .thenThrow(new AppException(ErrorCode.POST_NOT_FOUND,ErrorCode.POST_NOT_FOUND.getMessage()));

            mockMvc.perform(put("/api/v1/posts/1")
                            .with(csrf())
                            // json ???????????? ??????
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(modifyRequest)))
                    .andDo(print())
                    // ErrorCode??? ?????????????????? ????????? HttpStatus ??? ????????????
                    .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getHttpStatus().value()));
        }

        @Test
        @DisplayName("????????? ?????? ??????(3) - ????????? ?????????")
        @WithMockUser
        void post_modify_fail3() throws Exception {

            // ??????, postId, title, body -> token??? userName(?????????)??? ?????? ??? postId??? userName??? ?????? ??????
            when(postService.modify(any(),any(),any(),any()))
                    .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

            mockMvc.perform(put("/api/v1/posts/1")
                            .with(csrf())
                            // json ???????????? ??????
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(modifyRequest)))
                    .andDo(print())
                    // ErrorCode??? ?????????????????? ????????? HttpStatus ??? ????????????
                    .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
        }

        @Test
        @DisplayName("????????? ?????? ??????(4) - ?????????????????? ??????")
        @WithMockUser
        void post_modify_fail4() throws Exception {

            // ??????, postId, title, body -> ????????????????????? ????????? ????????? ???????????????????????? ????????? ????????? ????????? ??? ?????? ??????
            when(postService.modify(any(),any(),any(),any()))
                    .thenThrow(new AppException(ErrorCode.DATABASE_ERROR,ErrorCode.DATABASE_ERROR.getMessage()));

            mockMvc.perform(put("/api/v1/posts/1")
                            .with(csrf())
                            // json ???????????? ??????
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(modifyRequest)))
                    .andDo(print())
                    // ErrorCode??? ?????????????????? ????????? HttpStatus ??? ????????????
                    .andExpect(status().is(ErrorCode.DATABASE_ERROR.getHttpStatus().value()));
        }

    }

    @Nested
    class post_delete_test{
        @Test
        @DisplayName("????????? ?????? ??????")
        @WithMockUser
        void post_delete_success() throws Exception {

            mockMvc.perform(delete("/api/v1/posts/1")
                            .with(csrf())
                            // json ???????????? ??????
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(jsonPath("$.result.message").value("????????? ?????? ??????"))
                    .andExpect(jsonPath("$.result.postId").value(1L))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("????????? ?????? ??????(1) - ?????? ??????")
        @WithAnonymousUser
        void post_delete_fail1() throws Exception {
            // ??????, postId -> ??????.getName??? userName??? ?????? ??????
            when(postService.delete(any(),any()))
                    .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

            mockMvc.perform(delete("/api/v1/posts/1")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("????????? ?????? ??????(2) - ????????? ?????????")
        @WithMockUser
        void post_delete_fail2() throws Exception {
            // ??????, postId -> postId??? userId??? ??????
            when(postService.delete(any(),any()))
                    .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

            mockMvc.perform(delete("/api/v1/posts/1")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("????????? ?????? ??????(3) - ?????????????????? ??????")
        @WithMockUser
        void post_delete_fail3() throws Exception {
            // ??????????????? ????????? ????????? ???????????????????????? ????????? ????????? ????????? ??? ?????? ??????
            when(postService.delete(any(),any()))
                    .thenThrow(new AppException(ErrorCode.DATABASE_ERROR, ErrorCode.DATABASE_ERROR.getMessage()));

            mockMvc.perform(delete("/api/v1/posts/1")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().is(ErrorCode.DATABASE_ERROR.getHttpStatus().value()));
        }

    }








}