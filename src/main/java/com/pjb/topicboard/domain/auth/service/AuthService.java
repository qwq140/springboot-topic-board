package com.pjb.topicboard.domain.auth.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.pjb.topicboard.domain.auth.dto.request.JoinRequestDTO;
import com.pjb.topicboard.domain.auth.dto.request.LoginRequestDTO;
import com.pjb.topicboard.global.common.ErrorEnum;
import com.pjb.topicboard.global.common.TokenResponseDTO;
import com.pjb.topicboard.global.config.security.JwtProvider;
import com.pjb.topicboard.global.config.security.TokenType;
import com.pjb.topicboard.global.exception.CustomCommonException;
import com.pjb.topicboard.model.user.UserEntity;
import com.pjb.topicboard.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

        if(userRepository.existsByUsername(requestDTO.username()))
            throw new CustomCommonException(ErrorEnum.USERNAME_ALREADY_EXIST);


        if(userRepository.existsByNickname(requestDTO.nickname()))
            throw new CustomCommonException(ErrorEnum.NICKNAME_ALREADY_EXIST);

        UserEntity savedUser = userRepository.save(requestDTO.toEntity(passwordEncoder));

        String accessToken = jwtProvider.createToken(savedUser, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.createToken(savedUser, TokenType.REFRESH_TOKEN);

        return new TokenResponseDTO(accessToken, refreshToken);
    }

    public TokenResponseDTO login(LoginRequestDTO requestDTO) {
        UserEntity user = userRepository.findByUsername(requestDTO.username()).orElseThrow(
                () -> new CustomCommonException(ErrorEnum.INVALID_CREDENTIALS)
        );

        if(!passwordEncoder.matches(requestDTO.password(), user.getPassword())) {
            throw  new CustomCommonException(ErrorEnum.INVALID_CREDENTIALS);
        }

        String accessToken = jwtProvider.createToken(user, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.createToken(user, TokenType.REFRESH_TOKEN);

        return new TokenResponseDTO(accessToken, refreshToken);
    }

    public TokenResponseDTO reissueToken(String refreshToken) {
        try {
            DecodedJWT decodedJWT = jwtProvider.verify(refreshToken, TokenType.REFRESH_TOKEN);

            String username = decodedJWT.getClaim("username").asString();

            UserEntity user = userRepository.findByUsername(username).orElseThrow(
                    () ->  new CustomCommonException(ErrorEnum.INVALID_TOKEN)
            );

            String newAccessToken = jwtProvider.createToken(user, TokenType.ACCESS_TOKEN);
            String newRefreshToken = jwtProvider.createToken(user, TokenType.REFRESH_TOKEN);

            return new TokenResponseDTO(newAccessToken, newRefreshToken);

        } catch (Exception e) {
            throw new CustomCommonException(ErrorEnum.INVALID_TOKEN);
        }
    }
}
