package com.mutsasns.finalproject_kimmingyeong.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserDto {
    private Long id;
    private String userName;
}