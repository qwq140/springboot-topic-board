package com.pjb.topicboard.model.user;

import com.pjb.topicboard.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Table(name = "USER_TB")
@Entity
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    @ElementCollection(targetClass = UserRoleType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE_TB", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRoleType> role = new HashSet<>();

    @Builder
    public UserEntity(Long id, String username, String password, String nickname, Set<UserRoleType> role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }
}
