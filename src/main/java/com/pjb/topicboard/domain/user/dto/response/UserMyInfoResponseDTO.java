package com.pjb.topicboard.domain.user.dto.response;

import com.pjb.topicboard.model.user.UserEntity;
import lombok.Getter;

@Getter
public class UserMyInfoResponseDTO {
    private Long id;
    private String username;
    private String nickname;

    public UserMyInfoResponseDTO(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}
