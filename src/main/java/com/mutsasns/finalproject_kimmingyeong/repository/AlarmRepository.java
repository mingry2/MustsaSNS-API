package com.mutsasns.finalproject_kimmingyeong.repository;

import com.mutsasns.finalproject_kimmingyeong.domain.entity.Alarm;
import com.mutsasns.finalproject_kimmingyeong.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByUser(User user);
}
