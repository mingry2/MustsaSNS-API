package com.mutsasns.finalproject_kimmingyeong.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("김민경");
    }

    @GetMapping("/{num}")
    public  ResponseEntity<Integer> sum(@PathVariable Integer num) {
        return ResponseEntity.ok().body(num);
    }
}
