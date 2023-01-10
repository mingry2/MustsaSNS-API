package com.mutsasns.finalproject_kimmingyeong.domain.dto.alarm;

import com.mutsasns.finalproject_kimmingyeong.domain.entity.Alarm;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AlarmResponse {
    private Long id;
    private AlarmType alarmType;
    private Long fromUserId;
    private Long targetId;
    private String text;
    private LocalDateTime createdAt;

}
