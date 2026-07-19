package mutsa.api.global.config;

import lombok.RequiredArgsConstructor;
import mutsa.api.global.security.handler.OAuth2SuccessHandler;
import mutsa.api.global.security.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // ⭐ 서비스와 핸들러를 생성자 주입받기 위해 추가!
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    // Public API 경로들
    private final String[] publicAPI = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/v1/users/signup", // 회원가입 API
            "/api/v1/users/login", // 로그인 API
            "/api/v1/products", // 상품 전체 목록 조회 API
            "/api/v1/products/**", // 상품 상세 조회 API
            "/oauth2/**", // ⭐ 카카오 로그인 시작점 허용
            "/login/oauth2/**" // ⭐ 카카오 콜백 리다이렉트 주소 허용
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(publicAPI).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/users/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200);
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write("로그아웃이 완료되었습니다.");
                        })
                        .permitAll()
                )
                // ⭐ OAuth2 소셜 로그인 핵심 설정 추가!
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // 카카오 유저 정보 처리 로직 등록
                        )
                        .successHandler(oAuth2SuccessHandler) // 로그인 성공 시 프론트로 토큰 보내는 핸들러 등록
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}