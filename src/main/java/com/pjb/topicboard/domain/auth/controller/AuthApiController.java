package com.pjb.topicboard.domain.auth.controller;

import com.pjb.topicboard.domain.auth.dto.request.JoinRequestDTO;
import com.pjb.topicboard.domain.auth.dto.request.LoginRequestDTO;
import com.pjb.topicboard.domain.auth.dto.request.ReissueRequestDTO;
import com.pjb.topicboard.domain.auth.service.AuthService;
import com.pjb.topicboard.global.common.ErrorResponseDTO;
import com.pjb.topicboard.global.common.ResponseDTO;
import com.pjb.topicboard.global.common.TokenResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "인증 API", description = "인증 API에 대한 설명입니다.")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthApiController {

    private final AuthService authService;


    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "회원가입 실패 : 유효성 검사 실패, 닉네임 중복, 아이디 중복", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @PostMapping("/join")
    public ResponseEntity<ResponseDTO<TokenResponseDTO>> join(@Valid @RequestBody JoinRequestDTO requestDTO, BindingResult bindingResult) {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "회원가입 완료", authService.join(requestDTO)));
    }

    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "로그인 실패 : 아이디 혹은 비밀번호 입력하지 않음, 아이디나 비밀번호 틀림", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<TokenResponseDTO>> login(@Valid @RequestBody LoginRequestDTO requestDTO, BindingResult bindingResult) {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "로그인 완료", authService.login(requestDTO)));
    }

    @Operation(summary = "토큰 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "토큰 재발급 실패", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @PostMapping("/reissue")
    public ResponseEntity<ResponseDTO<TokenResponseDTO>> reissue(@RequestBody ReissueRequestDTO requestDTO) {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "토큰 재발급 완료", authService.reissueToken(requestDTO.refreshToken())));
    }
}
