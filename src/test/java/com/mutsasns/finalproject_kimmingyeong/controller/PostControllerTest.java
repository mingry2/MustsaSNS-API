package com.mutsasns.finalproject_kimmingyeong.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostCreateRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostListResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostModifyRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.User;
import com.mutsasns.finalproject_kimmingyeong.exception.AppException;
import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import com.mutsasns.finalproject_kimmingyeong.repository.UserRepository;
import com.mutsasns.finalproject_kimmingyeong.service.PostService;
import com.mutsasns.finalproject_kimmingyeong.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
// WebMvcTest는 jpa 기능과 전혀 관계없는 어노테이션이기 때문에 아래 @MockBean을 사용하여 jpa 기능을 확인할 수 있게 한다.
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    PostCreateRequest postCreateRequest = new PostCreateRequest("testTitle", "testBody");

    @Test
    @DisplayName("포스트 등록 완료")
    @WithMockUser
    void create() throws Exception {

        when(postService.create(any(), any(), any()))
                .thenReturn(PostCreateResponse.builder()
                        .message("포스트 등록 완료")
                        .build());

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postCreateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").value("포스트 등록 완료"));
    }

    @Test
    @DisplayName("포스트 작성 실패(1) - 인증실패(JWT를 Bearer Token으로 보내지 않은 경우")
    @WithMockUser
    void create_fail1() throws Exception {

        when(postService.create(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage()));

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postCreateRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.result.errorCode").value("INVALID_TOKEN"))
                .andExpect(jsonPath("$.result.message").value("잘못된 토큰입니다."));

    }

    @Test
    @DisplayName("포스트 작성 실패(2) - 인증실패(JWT가 유효하지 않은 경우)")
    @WithMockUser
    void create_fail2() throws Exception {

        when(postService.create(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage()));

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postCreateRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.result.errorCode").value("INVALID_TOKEN"))
                .andExpect(jsonPath("$.result.message").value("잘못된 토큰입니다."));

    }

    @Test
    @DisplayName("포스트 1개 조회 성공")
    @WithMockUser
    void post_get_post_success() throws Exception {

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
                // result 안에 감싸져있기 때문에 .result를 통해 경로를 지정해준다.
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
    @DisplayName("포스트 전체 조회 성공 - pageable 사용")
    @WithMockUser
    void post_get_post_all_success() throws Exception {

        mockMvc.perform(get("/api/v1/posts")
                .param("page", "0")
                .param("size", "3")
                .param("sort", "createdAt,desc"))
                .andExpect(status().isOk());

        // ArgumentCaptor 사용 -> 객체를 호출할 때 사용한 인자를 검증할 때 사용
        // 메소드 호출 여부를 검증하는 과정에서 실제 호출할 때 전달한 인자를 보관할 수 있음
        // capture() 메소드를 전달 getValue() 메소드로 실제 인자 값을 가져와서 검증
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(postService).getAll(pageableArgumentCaptor.capture());
        PageRequest pageRequest = (PageRequest) pageableArgumentCaptor.getValue();

        assertEquals(0, pageRequest.getPageNumber());
        assertEquals(3, pageRequest.getPageSize());
        assertEquals(Sort.by("createdAt", "desc"), pageRequest.withSort(Sort.by("createdAt","desc")).getSort());

    }

    @Test
    @DisplayName("포스트 수정 성공")
    @WithMockUser // 인증된 사용자를 테스트에서 사용할 경우
    void post_modify_success() throws Exception {

        // 수정한 title, body의 request
        PostModifyRequest modifyRequest = PostModifyRequest.builder()
                .title("test title")
                .body("test body")
                .build();

        // 수정할 postId
        Post post = Post.builder()
                .postId(1L)
                .build();

        // 인증, postId, title, body
        when(postService.modify(any(),any(),any(),any()))
                .thenReturn(post);

        mockMvc.perform(put("/api/v1/posts/1")
                .with(csrf())
                // json 형식으로 변경
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(modifyRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("포스트 수정 실패(1) - 인증 실패")
    @WithAnonymousUser // 인증되지 않은 사용자를 테스트에서 사용할 경우
    void post_modify_fail1() throws Exception {

        // 수정한 title, body의 request
        PostModifyRequest modifyRequest = PostModifyRequest.builder()
                .title("test title")
                .body("test body")
                .build();

        // 인증, postId, title, body -> '인증'인자가 권한없음
        when(postService.modify(any(),any(),any(),any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        // json 형식으로 변경
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(modifyRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    @DisplayName("포스트 수정 실패(2) - 포스트 내용 불일치")
    @WithMockUser
    void post_modify_fail2() throws Exception {

        // 수정한 title, body의 request
        PostModifyRequest modifyRequest = PostModifyRequest.builder()
                .title("test title")
                .body("test body")
                .build();

        // 인증, postId, title, body -> 'postId'로 넘어온 인자의 포스트가 없음
        when(postService.modify(any(),any(),any(),any()))
                .thenThrow(new AppException(ErrorCode.POST_NOT_FOUND,ErrorCode.POST_NOT_FOUND.getMessage()));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        // json 형식으로 변경
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(modifyRequest)))
                .andDo(print())
                // ErrorCode에 정의되어있는 에러의 HttpStatus 값 가져오기
                .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getHttpStatus().value()));

    }

    @Test
    @DisplayName("포스트 수정 실패(3) - 작성자 불일치")
    @WithMockUser
    void post_modify_fail3() throws Exception {

        // 수정한 title, body의 request
        PostModifyRequest modifyRequest = PostModifyRequest.builder()
                .title("test title")
                .body("test body")
                .build();

        // 인증, postId, title, body -> token의 userName과 수정할 postId의 userName이 다를 때
        when(postService.modify(any(),any(),any(),any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        // json 형식으로 변경
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(modifyRequest)))
                .andDo(print())
                // ErrorCode에 정의되어있는 에러의 HttpStatus 값 가져오기
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));

    }

    @Test
    @DisplayName("포스트 수정 실패(4) - 데이터베이스 에러")
    @WithMockUser
    void post_modify_fail4() throws Exception {

        // 수정한 title, body의 request
        PostModifyRequest modifyRequest = PostModifyRequest.builder()
                .title("test title")
                .body("test body")
                .build();

        // 인증, postId, title, body -> 수정완료하려고 하는데 갑자기 데이터베이스와의 연결이 끊어져 확인할 수 없는경우
        when(postService.modify(any(),any(),any(),any()))
                .thenThrow(new AppException(ErrorCode.DATABASE_ERROR,ErrorCode.DATABASE_ERROR.getMessage()));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        // json 형식으로 변경
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(modifyRequest)))
                .andDo(print())
                // ErrorCode에 정의되어있는 에러의 HttpStatus 값 가져오기
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getHttpStatus().value()));

    }

    @Test
    @DisplayName("포스트 삭제 성공")
    @WithMockUser
    void post_delete_success() throws Exception {

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        // json 형식으로 변경
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(status().isOk());

    }

//    @Test
//    @DisplayName("포스트 삭제 실패(1) - 인증 실패")
//    @WithAnonymousUser // 인증되지 않은 사용자를 테스트에서 사용할 경우
//    void post_delete_fail1() throws Exception {
//
//        // 수정한 title, body의 request
//        PostModifyRequest modifyRequest = PostModifyRequest.builder()
//                .title("test title")
//                .body("test body")
//                .build();
//
//        // 인증, postId, title, body -> '인증'인자가 권한없음
//        when(postService.modify(any(),any(),any(),any()))
//                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));
//
//        mockMvc.perform(put("/api/v1/posts/1")
//                        .with(csrf())
//                        // json 형식으로 변경
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(modifyRequest)))
//                .andDo(print())
//                .andExpect(status().isUnauthorized());
//
//    }



}