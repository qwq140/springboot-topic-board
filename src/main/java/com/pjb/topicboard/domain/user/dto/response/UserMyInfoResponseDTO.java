package com.pjb.topicboard.domain.user.dto.response;

import com.pjb.topicboard.model.user.UserEntity;
import com.pjb.topicboard.model.user.UserRoleType;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserMyInfoResponseDTO {
    private Long id;
    private String username;
    private String nickname;
    private Set<UserRoleType> roles;

    public UserMyInfoResponseDTO(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.roles = user.getRole();
    }
}
