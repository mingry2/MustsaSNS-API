package com.mutsasns.finalproject_kimmingyeong.domain.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlarmContainer {
    List<AlarmResponse> content;

}
