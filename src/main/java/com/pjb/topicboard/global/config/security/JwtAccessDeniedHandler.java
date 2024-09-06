package com.pjb.topicboard.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjb.topicboard.global.common.ErrorEnum;
import com.pjb.topicboard.global.common.ErrorResponseDTO;
import com.pjb.topicboard.global.common.ResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        sendErrorResponse(response, ErrorEnum.FORBIDDEN_USER);
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorEnum errorEnum) throws IOException {
        ObjectMapper om = new ObjectMapper();
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(errorEnum);
        String responseBody = om.writeValueAsString(responseDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(errorEnum.getStatus().value());
        response.getWriter().println(responseBody);
    }
}
