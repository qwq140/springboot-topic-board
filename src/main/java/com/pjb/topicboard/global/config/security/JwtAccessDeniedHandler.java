package com.pjb.topicboard.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        sendErrorResponse(response, "접근 권한이 없습니다.");
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        ObjectMapper om = new ObjectMapper();
        ResponseDTO responseDTO = new ResponseDTO(-1, message);
        String responseBody = om.writeValueAsString(responseDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println(responseBody);
    }
}
