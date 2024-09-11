package com.pjb.topicboard.domain.user.controller;

import com.pjb.topicboard.domain.user.dto.response.UserMyInfoResponseDTO;
import com.pjb.topicboard.domain.user.service.UserService;
import com.pjb.topicboard.global.common.ResponseDTO;
import com.pjb.topicboard.global.config.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/my")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        UserMyInfoResponseDTO responseDTO = userService.findMyInfo(customUserDetails);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "내 정보 조회", responseDTO));
    }
}
