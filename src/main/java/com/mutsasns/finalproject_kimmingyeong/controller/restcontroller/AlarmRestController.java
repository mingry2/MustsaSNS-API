package com.mutsasns.finalproject_kimmingyeong.controller.restcontroller;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.alarm.AlarmResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.response.Response;
import com.mutsasns.finalproject_kimmingyeong.service.AlarmService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
@Slf4j
@Data
public class AlarmRestController {

    private final AlarmService alarmService;

    // 받은 알람 조회
    @GetMapping("")
    public Response<Page<AlarmResponse>> alarm(Authentication authentication, @PageableDefault(size = 20) @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable){
        Page<AlarmResponse> list = alarmService.listAlarm(authentication.getName(), pageable);
        return Response.success(list);

    }
}
