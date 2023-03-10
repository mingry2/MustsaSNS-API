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
import org.springframework.transaction.annotation.Transactional;

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

    // ?????? ??????
    public CommentCreateResponse create(Long postId, CommentCreateRequest commentCreateRequest, String userName) {;

        // userName??? ????????? ??????
        User user = validator.validatorUser(userName);

        // postId??? ????????? ??????
        Post post = validator.validatorPost(postId);

        // ?????? ?????? ??????
        Comment comment = commentRepository.save(commentCreateRequest.toEntity(user, post));

        // ?????? ??????
        alarmRepository.save(Alarm.addAlarm(AlarmType.NEW_COMMENT_ON_POST, user, post));

        return comment.toCreateResponse();
    }

    // ?????? ??????
    public Page<CommentListResponse> getAll(Long postId, Pageable pageable) {

        // postId??? ????????? ??????
        Post post = validator.validatorPost(postId);

        // ???????????? ?????? ??????
        Page<Comment> commentList = commentRepository.findByPost(post, pageable);
        Page<CommentListResponse> commentListResponses = CommentListResponse.toResponse(commentList);

        return commentListResponses;
    }

    // ?????? ??????
    @Transactional
    public CommentModifyResponse modify(Long postId, Long id, CommentModifyRequest commentModifyRequest, String userName) {

        // userName??? ????????? ??????
        User user = validator.validatorUser(userName);

        // postId??? ????????? ??????
        Post post = validator.validatorPost(postId);

        // commentId??? ????????? ??????
        Comment comment = validator.validatorComment(id);

        // ????????? ????????? ?????? ??????????????? ?????? ??????
        Long loginUserId = user.getUserId();
        Long commentWriteUserId = comment.getUser().getUserId();

        validator.validatorEqualsUserId(commentWriteUserId, loginUserId);

        // ????????? ?????? DB ??????
        comment.setComment(commentModifyRequest.getComment());
        Comment savedComment = commentRepository.save(comment);

        return savedComment.toModifyResponse();
    }

    // ?????? ??????
    @Transactional
    public CommentDeleteResponse delete(Long postId, Long id, String userName) {

        // userName??? ????????? ??????
        User user = validator.validatorUser(userName);

        // postId??? ????????? ??????
        Post post = validator.validatorPost(postId);

        // commentId??? ????????? ??????
        Comment comment = validator.validatorComment(id);

        // ????????? ????????? ?????? ??????????????? ?????? ??????
        Long loginUserId = user.getUserId();
        Long commentWriteUserId = comment.getUser().getUserId();

        validator.validatorEqualsUserId(commentWriteUserId, loginUserId);

        // ?????? ??????
        commentRepository.deleteById(comment.getCommentId());

        return CommentDeleteResponse.builder()
                .message("?????? ?????? ??????")
                .id(comment.getCommentId())
                .build();
    }
}
