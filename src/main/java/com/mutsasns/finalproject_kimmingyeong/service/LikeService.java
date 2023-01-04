package com.mutsasns.finalproject_kimmingyeong.service;

import com.mutsasns.finalproject_kimmingyeong.domain.entity.Like;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.User;
import com.mutsasns.finalproject_kimmingyeong.exception.AppException;
import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import com.mutsasns.finalproject_kimmingyeong.repository.LikeRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.PostRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 좋아요 누르기
    public boolean addLike(Long postId, String userName) {

        // 로그인 하지 않은 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // 게시물이 존재하지 않는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // postId에 userName의 like가 있는지 확인(isPresent 사용을 위해 Optional로 구현)
        Optional<Like> optionalLike = likeRepository.findByUserAndPost(user, post);

        if (optionalLike.isPresent()){ // like가 존재한다면,
            likeRepository.delete(optionalLike.get()); // like를 삭제한다.
        }else { // like가 존재하지 않는다면,
            likeRepository.save(Like.addLike(user, post)); // like를 저장한다.
        }

        return true;

    }

    // 좋아요 개수
    public Long countLike(Long postId, String userName) {

        // 게시물이 존재하지 않는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // postId에 like 수 확인
        Long count = likeRepository.countByPost(post);

        return count;

    }
}
