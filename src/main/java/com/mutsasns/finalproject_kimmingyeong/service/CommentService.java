package com.mutsasns.finalproject_kimmingyeong.service;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.create.CommentCreateRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.create.CommentCreateResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.delete.CommentDeleteResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.list.CommentListResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.modify.CommentModifyRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.comment.modify.CommentModifyResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.*;
import com.mutsasns.finalproject_kimmingyeong.repository.AlarmRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.CommentRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.PostRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.UserRepository;
import com.mutsasns.finalproject_kimmingyeong.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AlarmRepository alarmRepository;
    private final Validator validator;
    public CommentService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, AlarmRepository alarmRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.alarmRepository = alarmRepository;
        this.validator = Validator.builder()
                .userRepository(userRepository)
                .postRepository(postRepository)
                .commentRepository(commentRepository)
                .build();

    }

    // ----------------------------------------------------------------------------------

    // 댓글 등록
    public CommentCreateResponse create(Long postId, CommentCreateRequest commentCreateRequest, String userName) {;

        // userName이 있는지 체크
        User user = validator.validatorUser(userName);

        // postId가 있는지 체크
        Post post = validator.validatorPost(postId);

        // 댓글 등록 완료
        Comment comment = commentRepository.save(commentCreateRequest.toEntity(user, post));

        // 알람 저장
        alarmRepository.save(Alarm.addAlarm(AlarmType.NEW_COMMENT_ON_POST, user, post));

        return comment.toCreateResponse();
    }

    // 댓글 조회
    public Page<CommentListResponse> getAll(Long postId, Pageable pageable) {

        // postId가 있는지 체크
        Post post = validator.validatorPost(postId);

        // 포스트의 댓글 조회
        Page<Comment> commentList = commentRepository.findByPost(post, pageable);
        Page<CommentListResponse> commentListResponses = CommentListResponse.toResponse(commentList);

        return commentListResponses;
    }

    // 댓글 수정
    public CommentModifyResponse modify(Long postId, Long id, CommentModifyRequest commentModifyRequest, String userName) {

        // userName이 있는지 체크
        User user = validator.validatorUser(userName);

        // postId가 있는지 체크
        Post post = validator.validatorPost(postId);

        // commentId가 있는지 체크
        Comment comment = validator.validatorComment(id);

        // 로그인 유저가 댓글 작성유저가 아닐 경우
        Long loginUserId = user.getUserId();
        Long commentWriteUserId = comment.getUser().getUserId();

        validator.validatorEqualsUserId(commentWriteUserId, loginUserId);

        // 수정한 댓글 DB 저장
        comment.setComment(commentModifyRequest.getComment());
        Comment savedComment = commentRepository.save(comment);

        return savedComment.toModifyResponse();
    }

    // 댓글 삭제
    public CommentDeleteResponse delete(Long postId, Long id, String userName) {

        // userName이 있는지 체크
        User user = validator.validatorUser(userName);

        // postId가 있는지 체크
        Post post = validator.validatorPost(postId);

        // commentId가 있는지 체크
        Comment comment = validator.validatorComment(id);

        // 로그인 유저가 댓글 작성유저가 아닐 경우
        Long loginUserId = user.getUserId();
        Long commentWriteUserId = comment.getUser().getUserId();

        validator.validatorEqualsUserId(commentWriteUserId, loginUserId);

        // 댓글 삭제
        commentRepository.deleteById(comment.getCommentId());

        return CommentDeleteResponse.builder()
                .message("댓글 삭제 완료")
                .id(comment.getCommentId())
                .build();
    }
}
