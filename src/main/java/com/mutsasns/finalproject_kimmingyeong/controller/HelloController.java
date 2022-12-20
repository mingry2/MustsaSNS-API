package com.mutsasns.finalproject_kimmingyeong.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    // http://localhost:8080/api/v1/hello
    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("hello2");
    }
}
