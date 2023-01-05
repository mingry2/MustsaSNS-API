package com.mutsasns.finalproject_kimmingyeong.service;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.create.CommentCreateRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.create.CommentCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.delete.CommentDeleteResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.list.CommentListResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.modify.CommentModifyRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.modify.CommentModifyResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.*;
import com.mutsasns.finalproject_kimmingyeong.exception.AppException;
import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import com.mutsasns.finalproject_kimmingyeong.repository.AlarmRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.CommentRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.PostRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AlarmRepository alarmRepository;

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

        // 알람 저장
        alarmRepository.save(Alarm.addAlarm(AlarmType.NEW_COMMENT_ON_POST, user, post));

        return comment.toCreateResponse();
    }

    // 댓글 조회
    public Page<CommentListResponse> getAll(Long postId, Pageable pageable) {
        // 게시물이 존재하지 않는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // 포스트의 댓글 조회
        Page<Comment> commentList = commentRepository.findByPost(post, pageable);
        Page<CommentListResponse> commentListResponses = CommentListResponse.toResponse(commentList);

        return commentListResponses;
    }

    // 댓글 수정
    public CommentModifyResponse modify(Long postId, Long id, CommentModifyRequest commentModifyRequest, String userName) {
        // 게시물이 존재하지 않는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // 댓글이 존재하지 않는 경우
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));

        // 댓글 작성자가 아닌 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Long userId = user.getUserId();

        if (!Objects.equals(comment.getUser().getUserId(), userId)){
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        // 수정한 댓글 DB 저장
        comment.setComment(commentModifyRequest.getComment());
        Comment savedComment = commentRepository.saveAndFlush(comment);

        return savedComment.toModifyResponse();
    }

    // 댓글 삭제
    public CommentDeleteResponse delete(Long postsId, Long id, String userName) {
        // 게시물이 존재하지 않는 경우
        Post post = postRepository.findById(postsId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // 댓글이 존재하지 않는 경우
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));

        // 댓글 작성자가 아닌 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Long userId = user.getUserId();

        if (!Objects.equals(comment.getUser().getUserId(), userId)){
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        // 댓글 삭제
        commentRepository.deleteById(comment.getCommentId());

        log.debug("comment.getCommentId : {}", comment.getCommentId());
        log.debug("postsId : {}", postsId);

        return CommentDeleteResponse.builder()
                .message("포스트 삭제 완료")
                .id(postsId)
                .build();
    }
}
