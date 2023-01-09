package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.service.AlgorithmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hello")
@RequiredArgsConstructor
public class HelloController {

    private final AlgorithmService algorithmService;

    // cicd 체크용
    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("다시");
    }

    // 20221229 실습 -> 자릿수 합 구하기
    @GetMapping("/{num}")
    public  ResponseEntity<Integer> sum(@PathVariable Integer num) {
        return ResponseEntity.ok().body(algorithmService.sumOfDigit(num));
    }
}
