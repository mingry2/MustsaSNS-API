package com.mutsasns.finalproject_kimmingyeong.domain.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@javax.persistence.Entity
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String userName;
    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime removeAt;
    private LocalDateTime registeredAt;
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comment = new ArrayList<>();


}
