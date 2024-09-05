package com.pjb.topicboard.domain.auth.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.pjb.topicboard.domain.auth.dto.request.JoinRequestDTO;
import com.pjb.topicboard.domain.auth.dto.request.LoginRequestDTO;
import com.pjb.topicboard.global.common.TokenResponseDTO;
import com.pjb.topicboard.global.config.security.JwtProvider;
import com.pjb.topicboard.global.config.security.TokenType;
import com.pjb.topicboard.global.exception.Exception400;
import com.pjb.topicboard.global.exception.Exception401;
import com.pjb.topicboard.model.user.UserEntity;
import com.pjb.topicboard.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponseDTO join(JoinRequestDTO requestDTO) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(requestDTO.username());

        if(userOptional.isPresent()) {
            throw new Exception400("이미 존재하는 아이디 입니다.");
        }

        UserEntity savedUser = userRepository.save(requestDTO.toEntity(passwordEncoder));

        String accessToken = jwtProvider.createToken(savedUser, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.createToken(savedUser, TokenType.REFRESH_TOKEN);

        return new TokenResponseDTO(accessToken, refreshToken);
    }

    public TokenResponseDTO login(LoginRequestDTO requestDTO) {
        UserEntity user = userRepository.findByUsername(requestDTO.username()).orElseThrow(
                () -> new Exception400("아이디 또는 비밀번호가 잘못되었습니다.")
        );

        if(!passwordEncoder.matches(requestDTO.password(), user.getPassword())) {
            throw new Exception400("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        String accessToken = jwtProvider.createToken(user, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.createToken(user, TokenType.REFRESH_TOKEN);

        return new TokenResponseDTO(accessToken, refreshToken);
    }

    public TokenResponseDTO reissueToken(String refreshToken) {
        log.debug(refreshToken);
        try {
            DecodedJWT decodedJWT = jwtProvider.verify(refreshToken, TokenType.REFRESH_TOKEN);

            String username = decodedJWT.getClaim("username").asString();

            UserEntity user = userRepository.findByUsername(username).orElseThrow(
                    () -> new Exception401("올바르지 않은 토큰입니다.")
            );

            String newAccessToken = jwtProvider.createToken(user, TokenType.ACCESS_TOKEN);
            String newRefreshToken = jwtProvider.createToken(user, TokenType.REFRESH_TOKEN);

            return new TokenResponseDTO(newAccessToken, newRefreshToken);

        } catch (Exception e) {
            throw new Exception401("올바르지 않은 토큰입니다.");
        }
    }
}
