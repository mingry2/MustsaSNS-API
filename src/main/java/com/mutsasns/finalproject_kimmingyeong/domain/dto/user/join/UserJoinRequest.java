package com.mutsasns.finalproject_kimmingyeong.domain.dto.user.join;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 회원가입 시 User에게 받아올 정보
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class UserJoinRequest {
    private String userName; // 로그인 시 사용할 user name
    private String password; // 로그인 시 사용할 user password
}
