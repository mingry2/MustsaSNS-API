package com.mutsasns.finalproject_kimmingyeong.domain.dto;

import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private ErrorCode errorCode;
    private String message;
}
