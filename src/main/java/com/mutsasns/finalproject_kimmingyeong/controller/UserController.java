package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.user.join.UserJoinRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.user.join.UserJoinResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.user.login.UserLoginRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.user.login.UserLoginResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.response.Response;
import com.mutsasns.finalproject_kimmingyeong.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest){
        log.debug("userName : {} password : {}  ", userJoinRequest.getUserName(), userJoinRequest.getPassword());
        UserJoinResponse userJoinResponse = userService.join(userJoinRequest.getUserName(), userJoinRequest.getPassword());
        log.debug("userJoinResponse : {} ", userJoinResponse);
        return Response.success(userJoinResponse);
    }

    // 로그인
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        String token = userService.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
