package com.pjb.topicboard.domain.auth.dto.request;

import com.pjb.topicboard.model.user.UserEntity;
import com.pjb.topicboard.model.user.UserRoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

public record JoinRequestDTO(
        @NotBlank(message = "아이디를 입력해주세요")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{4,12}$", message = "아이디는 영문자와 숫자를 조합하여 4~12자 사용 가능합니다.")
        String username,
        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W).{8,20}$", message = "비밀번호는 영문, 숫자, 특수문자를 조합하여 8~20자 사용 가능합니다.")
        String password,
        @NotBlank(message = "닉네임을 입력해주세요")
        String nickname
) {
    public UserEntity toEntity(PasswordEncoder passwordEncoder) {
        Set<UserRoleType> role = new HashSet<>();
        role.add(UserRoleType.USER);
        return UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(role)
                .build();
    }

}
