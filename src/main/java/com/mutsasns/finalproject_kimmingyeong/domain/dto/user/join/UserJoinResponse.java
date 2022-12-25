package com.mutsasns.finalproject_kimmingyeong.domain.dto.user.join;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserJoinResponse {
    private Long userId;
    private String userName;

}
