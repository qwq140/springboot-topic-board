package com.pjb.topicboard.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        Object exceptionMessage = request.getAttribute("exception");
        String message = exceptionMessage != null ? exceptionMessage.toString() : "인증 되지 않은 사용자 입니다.";

        sendErrorResponse(response, message);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        ObjectMapper om = new ObjectMapper();
        ResponseDTO responseDTO = new ResponseDTO(-1, message);
        String responseBody = om.writeValueAsString(responseDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(responseBody);
    }
}
