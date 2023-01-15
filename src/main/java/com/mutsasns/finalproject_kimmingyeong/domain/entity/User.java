package com.mutsasns.finalproject_kimmingyeong.domain.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
//@Where(clause = "remove_at is NULL")
//@SQLDelete(sql = "UPDATE user SET remove_at = current_timestamp WHERE user_id = ?")
public class User extends UserBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String userName;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "remove_at")
    private LocalDateTime removeAt; // 삭제된 시간 -> 회원 탈퇴 구현 시 사용


}
