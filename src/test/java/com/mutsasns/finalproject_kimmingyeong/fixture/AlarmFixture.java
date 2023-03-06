package com.mutsasns.finalproject_kimmingyeong.fixture;

import com.mutsasns.finalproject_kimmingyeong.domain.entity.Alarm;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.AlarmType;

public class AlarmFixture {
    public static Alarm getCommentAlarm(Long fromUserId, Long targetId) {
        Alarm alarm = Alarm.builder()
                .alarmType(AlarmType.NEW_COMMENT_ON_POST)
                .fromUserId(fromUserId)
                .targetId(targetId)
                .text("new comment!")
                .build();

        return alarm;
    }

}
