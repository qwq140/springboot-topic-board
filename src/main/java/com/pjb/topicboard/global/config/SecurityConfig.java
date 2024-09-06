package com.pjb.topicboard.global.config;

import com.pjb.topicboard.global.config.security.*;
import com.pjb.topicboard.model.user.UserRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(configurer -> configurer.configurationSource(corsConfiguration()));

        http.authorizeHttpRequests(config-> config
                .requestMatchers(PathRequest.toH2Console())
                .permitAll()
        );

        http.addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(config -> config
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(new JwtAccessDeniedHandler())
        );

        http.authorizeHttpRequests(config -> config
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, UrlRoleMapping.PUBLIC_GET_URLS).permitAll()
                .requestMatchers(HttpMethod.POST, UrlRoleMapping.ADMIN_POST_URLS).hasRole(UserRoleType.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, UrlRoleMapping.ADMIN_PUT_URLS).hasRole(UserRoleType.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, UrlRoleMapping.ADMIN_DELETE_URLS).hasRole(UserRoleType.ADMIN.name())
                .anyRequest().authenticated()
        );

        return http.build();

    }

    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE 자바스크립트 요청 허용
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader(JwtConstants.HEADER);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
