package com.mutsasns.finalproject_kimmingyeong.service;

import com.mutsasns.finalproject_kimmingyeong.domain.dto.alarm.AlarmResponse;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.Alarm;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.User;
import com.mutsasns.finalproject_kimmingyeong.repository.AlarmRepository;
import com.mutsasns.finalproject_kimmingyeong.repository.UserRepository;
import com.mutsasns.finalproject_kimmingyeong.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class AlarmService {
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private Validator validator;
    private AlarmService(UserRepository userRepository, AlarmRepository alarmRepository){
        this.userRepository = userRepository;
        this.alarmRepository = alarmRepository;
        this.validator = Validator.builder()
                .userRepository(userRepository)
                .build();
    }

    // ----------------------------------------------------------------------------------

    public List<AlarmResponse> listAlarm(String userName, Pageable pageable) {

        // userName이 있는지 체크
        User user = validator.validatorUser(userName);

        // userName이 받은 알람 조회
        List<Alarm> alarms = alarmRepository.findAllByUser(user);

        List<AlarmResponse> alarmResponses = alarms.stream().map(alarm -> alarm.toResponse()).collect(Collectors.toList());

        return alarmResponses;
    }
}
