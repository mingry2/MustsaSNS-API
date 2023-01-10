package com.mutsasns.finalproject_kimmingyeong.service;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.alarm.AlarmContainer;
import com.mutsasns.finalproject_kimmingyeong.domain.dto.alarm.AlarmResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Alarm;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.User;
import com.mutsasns.finalproject_kimmingyeong.exception.AppException;
import com.mutsasns.finalproject_kimmingyeong.exception.ErrorCode;
import com.mutsasns.finalproject_kimmingyeong.repository.AlarmRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    public List<AlarmResponse> listAlarm(String userName, Pageable pageable) {

        // userName이 없는 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // userName이 받은 알람 조회
        List<Alarm> alarms = alarmRepository.findAllByUser(user);

        List<AlarmResponse> alarmResponses = alarms.stream().map(alarm -> alarm.toResponse()).collect(Collectors.toList());

        return alarmResponses;
    }
}
