package mutsa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.UserLoginRequestDto;
import mutsa.api.dto.UserSignupRequestDto;
import mutsa.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API", description = "회원 및 인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    // 수동 로그인시 세션 생성을 보장하는 리포지토리 객체
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Operation(summary = "신규 회원가입", description = "이메일, 비밀번호, 이름을 입력받아 신규 회원을 등록합니다.")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequestDto requestDto) {
        userService.signUp(
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getName()
        );
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 JSON 데이터로 받아 로그인을 진행하고 세션을 유지합니다.")
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody UserLoginRequestDto requestDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // 클라이언트가 보낸 이메일, 비밀번호로 시큐리티용 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

        // 인증 관리자에게 인증 위임
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 인증이 성공하면 SecurityContext 생성 및 설정
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // 세션(HttpSession)에 시큐리티 인증 상태를 명시적으로 저장하여 로그인 상태가 풀리지 않도록 함
        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.ok("로그인이 성공적으로 완료되었습니다.");

    }

}
