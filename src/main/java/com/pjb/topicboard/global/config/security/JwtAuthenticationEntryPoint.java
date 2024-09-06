package com.pjb.topicboard.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjb.topicboard.global.common.ErrorEnum;
import com.pjb.topicboard.global.common.ErrorResponseDTO;
import com.pjb.topicboard.global.common.ResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exceptionMessage = (String) request.getAttribute("exception");
        ErrorEnum error = exceptionMessage != null ? ErrorEnum.valueOf(exceptionMessage) : ErrorEnum.UNAUTHORIZED_USER;

        sendErrorResponse(response, error);
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
