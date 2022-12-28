package com.mutsasns.finalproject_kimmingyeong.domain.dto.user.join;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Data
public class UserJoinResponse {
    private Long userId;
    private String userName;

}
