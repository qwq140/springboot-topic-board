package com.pjb.topicboard.domain.auth.controller;

import com.pjb.topicboard.domain.auth.dto.request.JoinRequestDTO;
import com.pjb.topicboard.domain.auth.dto.request.LoginRequestDTO;
import com.pjb.topicboard.domain.auth.dto.request.ReissueRequestDTO;
import com.pjb.topicboard.domain.auth.service.AuthService;
import com.pjb.topicboard.global.common.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinRequestDTO requestDTO, BindingResult bindingResult) {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "회원가입 완료", authService.join(requestDTO)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO requestDTO, BindingResult bindingResult) {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "로그인 완료", authService.login(requestDTO)));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody ReissueRequestDTO requestDTO) {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "토큰 재발급 완료", authService.reissueToken(requestDTO.refreshToken())));
    }
}
