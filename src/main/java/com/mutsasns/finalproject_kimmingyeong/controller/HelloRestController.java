package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.service.AlgorithmService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/hello")
@RequiredArgsConstructor
public class HelloRestController {

    private final AlgorithmService algorithmService;

    // cicd 체크용
    @ApiOperation(
            value = "CICD 체크용"
            , notes = "body([도전 단어])")
    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("김민경");
    }

    // 20221229 실습 -> 자릿수 합 구하기
    @ApiOperation(
            value = "20221229 실습"
            , notes = "자릿수 합 구하기")
    @GetMapping("/{num}")
    public  ResponseEntity<Integer> sum(@PathVariable Integer num) {
        return ResponseEntity.ok().body(algorithmService.sumOfDigit(num));
    }
}
