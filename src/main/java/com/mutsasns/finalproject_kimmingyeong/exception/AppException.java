package com.mutsasns.finalproject_kimmingyeong.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException{

    private ErrorCode errorCode; // 에러 코드 리스트
    private String message; // 에러 메세지
}
