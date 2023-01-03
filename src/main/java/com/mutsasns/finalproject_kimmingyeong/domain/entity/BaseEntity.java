package com.mutsasns.finalproject_kimmingyeong.domain.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Service
@MappedSuperclass // [JPA] 부모 클래스는 테이블과 매핑하지 않고 부모 클래스를 상속받는 자식 클래스에게 매핑 정보만 제공
@EntityListeners(AuditingEntityListener.class)
@Slf4j
public class BaseEntity {
    @CreatedDate // [jpa] Entity가 생성되어 저장될 때 시간 자동 저장
    @Column(updatable = false) // updatable = false : column 수정 시 값 변경을 막는다.
    private LocalDateTime createdAt;

    @LastModifiedDate // [jpa] 수정된 시간 정보를 자동으로 저장
    private LocalDateTime lastModifiedAt; // 수정된 시간 정보

}