package com.mutsasns.finalproject_kimmingyeong.domain.dto.user.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserLoginResponse {
    private String jwt;
}
