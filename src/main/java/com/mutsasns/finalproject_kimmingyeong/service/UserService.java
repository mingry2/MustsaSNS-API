package com.mutsasns.finalproject_kimmingyeong.service;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.user.join.UserJoinResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.User;
import com.mutsasns.finalproject_kimmingyeong.exception.AppException;
import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import com.mutsasns.finalproject_kimmingyeong.repository.UserRepository;
import com.mutsasns.finalproject_kimmingyeong.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;
//    private Long expiredTimeMs = 1000 * 60 * 60L; // 1시간
    private Long expiredTimeMs = 1 * 1000 * 60 * 60L * 24; // 하루

    // 회원가입
    public UserJoinResponse join(String userName, String password) {
        // userName 중복 체크
        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.DUPLICATED_USER_NAME, ErrorCode.DUPLICATED_USER_NAME.getMessage());
                });

        // DB 저장
        User user = User.builder()
                .userName(userName)
                .password(encoder.encode(password))
                .build();
        userRepository.save(user);
        log.debug("User: {}", user);

        return UserJoinResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .build();
    }

    // 로그인
    public String login(String userName, String password) {
        // userName 없음
        User findUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // password 틀림
        log.info("findUserName : {} findPassword : {} ", findUser.getPassword(), findUser.getPassword());
        if (!encoder.matches(password, findUser.getPassword()))
            throw new AppException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());

        // 성공 -> token 발행
        String token = JwtTokenUtil.createToken(findUser.getUserName(), secretKey, expiredTimeMs);

        return token;
    }
}
