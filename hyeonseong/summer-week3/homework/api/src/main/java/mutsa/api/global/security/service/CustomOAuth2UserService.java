package mutsa.api.global.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mutsa.api.domain.User;
import mutsa.api.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 카카오 서버로부터 사용자 정보 수신
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId(); // "kakao"
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 2. 카카오 고유 식별 ID
        String providerId = String.valueOf(attributes.get("id"));

        // 3. 닉네임 추출
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String nickname = "카카오유저";
        if (properties != null && properties.get("nickname") != null) {
            nickname = String.valueOf(properties.get("nickname"));
        }

        log.info("카카오 로그인 시도 -> provider: {}, providerId: {}, nickname: {}", provider, providerId, nickname);

        // 4. DB에서 유저 조회 후 없으면 자동 회원가입 진행
        final String finalNickname = nickname;
        User user = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> {
                    log.info("가입되지 않은 카카오 유저입니다. 자동 회원가입을 진행합니다.");

                    // DB의 email 컬럼이 nullable = false 이므로, 임시 이메일 생성하여 저장
                    String dummyEmail = provider + "_" + providerId + "@social.login";

                    User newUser = User.builder()
                            .email(dummyEmail)
                            .password("") // 비밀번호 불필요
                            .name(finalNickname)
                            .provider(provider)
                            .providerId(providerId)
                            .build();

                    return userRepository.save(newUser);
                });

        // 5. 시큐리티가 사용할 인가 객체 반환 (카카오는 식별 기준 컬럼이 "id")
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                userRequest.getClientRegistration().getProviderDetails()
                        .getUserInfoEndpoint().getUserNameAttributeName()
        );
    }
}