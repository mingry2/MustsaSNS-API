package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.Response;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.UserDto;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.UserJoinRequest;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.UserJoinResponse;
import com.mutsasns.finalproject_kimmingyeong.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest){
        UserDto userDto = userService.join(userJoinRequest.getUserName(), userJoinRequest.getPassword());
        return Response.success(new UserJoinResponse(userDto.getId(), userDto.getUserName()));

    }
}
