package com.pjb.topicboard.domain.user.service;

import com.pjb.topicboard.domain.user.dto.response.UserMyInfoResponseDTO;
import com.pjb.topicboard.global.common.ErrorEnum;
import com.pjb.topicboard.global.config.security.CustomUserDetails;
import com.pjb.topicboard.global.exception.CustomCommonException;
import com.pjb.topicboard.model.user.UserEntity;
import com.pjb.topicboard.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserMyInfoResponseDTO findMyInfo(CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUser().getId();
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.USER_NOT_FOUND)
        );

        return new UserMyInfoResponseDTO(user);
    }
}
