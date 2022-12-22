package com.mutsasns.finalproject_kimmingyeong.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {
    private String resultCode;
    private T result;

    public static <T> Response<T> error(String resultCode, T result){
        return new Response<>(resultCode, result);
    }

    public static <T> Response<T> success(T result) {
        return new Response("SUCCESS", result);
    }

    private static Response<Void> error(String resultCode) {
        return new Response(resultCode, null);
    }


}
