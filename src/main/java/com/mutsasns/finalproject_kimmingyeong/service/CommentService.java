package com.mutsasns.finalproject_kimmingyeong.service;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.CommentCreateRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.CommentCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Comment;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.User;
import com.mutsasns.finalproject_kimmingyeong.exception.AppException;
import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import com.mutsasns.finalproject_kimmingyeong.repository.CommentRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.PostRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 등록
    public CommentCreateResponse create(Long postId, CommentCreateRequest commentCreateRequest, String userName) {
        log.debug("postId : {} requestComment : {} userName : {}", postId, commentCreateRequest, userName);

        // 로그인 하지 않은 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // 게시물이 존재하지 않는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // 댓글 등록 완료
        Comment comment = commentRepository.save(commentCreateRequest.toEntity(user, post));

        return comment.toResponse();
    }
}
