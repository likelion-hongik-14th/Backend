package mutsa.week2.common.auth;

import lombok.RequiredArgsConstructor;
import mutsa.week2.member.Member;
import mutsa.week2.member.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CurrentMemberProvider implements ApplicationRunner {

    private static final String DEFAULT_MEMBER_NAME = "기본 사용자";

    private final MemberRepository memberRepository;

    private Long currentMemberId;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Member member = memberRepository.findAll().stream()
                .filter(m -> DEFAULT_MEMBER_NAME.equals(m.getName()))
                .findFirst()
                .orElseGet(() -> memberRepository.save(
                        Member.builder().name(DEFAULT_MEMBER_NAME).build()));
        this.currentMemberId = member.getId();
    }

    public Long currentMemberId() {
        return currentMemberId;
    }
}
