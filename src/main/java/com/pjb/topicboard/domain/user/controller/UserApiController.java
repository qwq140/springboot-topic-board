package com.pjb.topicboard.domain.user.controller;

import com.pjb.topicboard.domain.user.dto.response.UserMyInfoResponseDTO;
import com.pjb.topicboard.domain.user.service.UserService;
import com.pjb.topicboard.global.common.ErrorResponseDTO;
import com.pjb.topicboard.global.common.ResponseDTO;
import com.pjb.topicboard.global.config.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "유저 관련 API", description = "유저 관련 API에 대한 설명입니다.")
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserApiController {

    private final UserService userService;

    @Operation(summary = "내 정보 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "내 정보 조회 실패 : 존재 하지 않은 사용자 입니다.", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/my")
    public ResponseEntity<ResponseDTO<UserMyInfoResponseDTO>> getMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        UserMyInfoResponseDTO responseDTO = userService.findMyInfo(customUserDetails);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "내 정보 조회", responseDTO));
    }
}
