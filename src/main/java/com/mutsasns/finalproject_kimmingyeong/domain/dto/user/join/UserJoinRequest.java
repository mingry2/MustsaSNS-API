package com.mutsasns.finalproject_kimmingyeong.domain.dto.user.join;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class UserJoinRequest {
    private String userName;
    private String password;
}
