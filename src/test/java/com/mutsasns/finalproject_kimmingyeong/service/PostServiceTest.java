package com.mutsasns.finalproject_kimmingyeong.service;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.post.PostListResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.User;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.UserRole;
import com.mutsasns.finalproject_kimmingyeong.exception.AppException;
import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import com.mutsasns.finalproject_kimmingyeong.repository.PostRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


// 어노테이션 없이 test가 가능하게 해야함 -> pojo test
class PostServiceTest {

    PostService postService;

    // Mockito.mock을 이용하여 DB 디펜던시를 뺌 -> 스프링과 DB에 종속적이지 않음
    PostRepository postRepository = mock(PostRepository.class);
    UserRepository userRepository = mock(UserRepository.class);

    // 모든 test 실행전 먼저 실행되는 어노테이션
    @BeforeEach
    void setUp() {
        // new로 객체를 사용함
        postService = new PostService(postRepository, userRepository);
    }

    @Test
    @DisplayName("포스트 1개 조회 성공")
    void get_post_success() {
        // fixture 테스트에서만 사용하는 객체로 테스트의 코드 줄 수를 줄이기 위해 사용
        String userName = "user";
        String password = "1q2w3e4r";

        User user = User.builder()
                .userId(1L)
                .userName(userName)
                .password(password)
                .role(UserRole.USER)
                .registeredAt(LocalDateTime.now())
                .build();

        Post post = Post.builder()
                .postId(1L)
                .user(user)
                .title("test title")
                .body("test body")
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostListResponse postListResponse = postService.getPost(1L);
        assertEquals(user.getUserName(), postListResponse.getUserName());
    }

    @Test
    @DisplayName("포스트 등록 성공")
    void create_post_success() {
        String userName = "user";
        String password = "1q2w3e4r";

        User user = User.builder()
                .userId(1L)
                .userName(userName)
                .password(password)
                .role(UserRole.USER)
                .registeredAt(LocalDateTime.now())
                .build();

        Post post = Post.builder()
                .postId(1L)
                .user(user)
                .title("test title")
                .body("test body")
                .build();

        User mockUser = mock(User.class);
        Post mockPost = mock(Post.class);

        when(userRepository.findByUserName(user.getUserName()))
                .thenReturn(Optional.of(mockUser));
        when(postRepository.findById(post.getPostId()))
                .thenReturn(Optional.of(mockPost));

        // assertDoesNotThrow : 에러가 발생하지 않으면 True
        Assertions.assertDoesNotThrow(() -> postService.create(post.getTitle(), post.getBody(), post.getUser().getUserName()));
    }

    @Test
    @DisplayName("포스트 등록 실패 - 유저가 존재하지 않을 때")
    void create_post_fail() {
        String userName = "user";
        String password = "1q2w3e4r";

        User user = User.builder()
                .userId(1L)
                .userName(userName)
                .password(password)
                .role(UserRole.USER)
                .registeredAt(LocalDateTime.now())
                .build();

        Post post = Post.builder()
                .postId(1L)
                .user(user)
                .title("test title")
                .body("test body")
                .build();

        when(userRepository.findByUserName(user.getUserName()))
                .thenReturn(Optional.empty());
        when(postRepository.save(any()))
                .thenReturn(mock(Post.class));

        // Assertions -> 테스트가 원하는 결과를 제대로 리턴하는지 에러를 발생하지 않는지 확인할 때 사용하는 메소드
        // .assertThrows -> 예상한 에러가 발생하면 True
        AppException exception = Assertions.assertThrows(AppException.class,
                () -> postService.create(post.getUser().getUserName(), post.getTitle(),post.getBody()));

        assertEquals(ErrorCode.USERNAME_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    @DisplayName("포스트 수정 실패(1): 작성자!=유저")
    void modify_fail1(){
        String userName1 = "userName1";
        String password1 = "1q2w3e4r1";

        String userName2 = "userName2";
        String password2 = "1q2w3e4r2";

        User user1 = User.builder()
                .userId(1L)
                .userName(userName1)
                .password(password1)
                .role(UserRole.USER)
                .registeredAt(LocalDateTime.now())
                .build();

        User user2 = User.builder()
                .userId(2L)
                .userName(userName2)
                .password(password2)
                .role(UserRole.USER)
                .registeredAt(LocalDateTime.now())
                .build();

        Post post = Post.builder()
                .postId(1L)
                .user(user1)
                .title("test title")
                .body("test body")
                .build();

        when(userRepository.findByUserName(user1.getUserName()))
                .thenReturn(Optional.of(user2));

        when(postRepository.findById(post.getPostId()))
                .thenReturn(Optional.of(post));

        AppException exception = Assertions.assertThrows(AppException.class,
                () -> postService.modify(post.getUser().getUserName(), post.getPostId(), post.getTitle(), post.getBody()));

        assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());

    }

    @Test
    @DisplayName("포스트 수정 실패(2): 포스트 존재하지 않음")
    void modify_fail2(){
        String userName = "user";
        String password = "1q2w3e4r";

        User user = User.builder()
                .userId(1L)
                .userName(userName)
                .password(password)
                .role(UserRole.USER)
                .registeredAt(LocalDateTime.now())
                .build();

        Post post = Post.builder()
                .postId(1L)
                .user(user)
                .title("test title")
                .body("test body")
                .build();

        when(postRepository.findById(post.getPostId()))
                .thenReturn(Optional.empty());

        AppException exception = Assertions.assertThrows(AppException.class,
                // String.valueOf()를 사용하면 전달받은 파라미터가 null이 전달될 경우 문자열 "null"을 반환
                () -> postService.modify(String.valueOf(post.getUser().getUserId()), post.getPostId(), post.getTitle(), post.getBody()));

        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    @DisplayName("포스트 수정 실패(3): 유저 존재하지 않음")
    void modify_fail3(){
        String userName = "user";
        String password = "1q2w3e4r";

        User user = User.builder()
                .userId(1L)
                .userName(userName)
                .password(password)
                .role(UserRole.USER)
                .registeredAt(LocalDateTime.now())
                .build();

        Post post = Post.builder()
                .postId(1L)
                .user(user)
                .title("test title")
                .body("test body")
                .build();

        when(postRepository.findById(post.getPostId()))
                .thenReturn(Optional.of(mock(Post.class)));
        when(userRepository.findByUserName(post.getUser().getUserName()))
                .thenReturn(Optional.empty());

        AppException exception = Assertions.assertThrows(AppException.class,
                // String.valueOf()를 사용하면 전달받은 파라미터가 null이 전달될 경우 문자열 "null"을 반환
                () -> postService.modify(String.valueOf(post.getUser().getUserId()), post.getPostId(), post.getTitle(), post.getBody()));

        assertEquals(ErrorCode.USERNAME_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    @DisplayName("포스트 삭제 성공")
    void post_delete_success() {
        String userName = "user";
        String password = "1q2w3e4r";

        User user = User.builder()
                .userId(1L)
                .userName(userName)
                .password(password)
                .role(UserRole.USER)
                .registeredAt(LocalDateTime.now())
                .build();

        Post post = Post.builder()
                .postId(1L)
                .user(user)
                .title("test title")
                .body("test body")
                .build();

        Post mockPost = mock(Post.class);
        User mockUser = mock(User.class);

        when(postRepository.findById(post.getPostId()))
                .thenReturn(Optional.of(mockPost));
        System.out.println(post.getPostId());

        when(userRepository.findByUserName(post.getUser().getUserName()))
                .thenReturn(Optional.of(user));
        System.out.println(post.getUser().getUserName());

        when(mockPost.getUser()).thenReturn(user);

        System.out.println(user.getUserId());
        System.out.println(user.getUserName());

        boolean b = postService.delete(post.getPostId(), user.getUserName());
        assertTrue(b);

    }

    @Test
    @DisplayName("포스트 삭제 실패(1): 유저 존재하지 않음")
    void post_delete_fail1() {
        String userName = "user";
        String password = "1q2w3e4r";

        User user = User.builder()
                .userId(1L)
                .userName(userName)
                .password(password)
                .role(UserRole.USER)
                .registeredAt(LocalDateTime.now())
                .build();

        Post post = Post.builder()
                .postId(1L)
                .user(user)
                .title("test title")
                .body("test body")
                .build();

        Post mockPost = mock(Post.class);
        User mockUser = mock(User.class);

        when(postRepository.findById(post.getPostId()))
                .thenReturn(Optional.of(mockPost));

        when(userRepository.findByUserName(post.getUser().getUserName()))
                .thenReturn(Optional.empty());

        AppException exception = Assertions.assertThrows(AppException.class, () -> postService.delete(post.getPostId(), post.getUser().getUserName()));
        assertEquals(ErrorCode.USERNAME_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    @DisplayName("포스트 삭제 실패(2): 포스트 존재하지 않음")
    void post_delete_fail2() {
        String userName = "user";
        String password = "1q2w3e4r";

        User user = User.builder()
                .userId(1L)
                .userName(userName)
                .password(password)
                .role(UserRole.USER)
                .registeredAt(LocalDateTime.now())
                .build();

        Post post = Post.builder()
                .postId(1L)
                .user(user)
                .title("test title")
                .body("test body")
                .build();

        Post mockPost = mock(Post.class);
        User mockUser = mock(User.class);

        when(postRepository.findById(post.getPostId()))
                .thenReturn(Optional.empty());

        when(userRepository.findByUserName(post.getUser().getUserName()))
                .thenReturn(Optional.of(user));

        AppException exception = Assertions.assertThrows(AppException.class, () -> postService.delete(post.getPostId(), post.getUser().getUserName()));
        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());

    }

}