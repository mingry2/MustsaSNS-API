package com.mutsasns.finalproject_kimmingyeong.domain.dto.response;

import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorResponse {
    private ErrorCode errorCode;
    private String message;
}
