package mutsa.mutsa_week5_hw;

import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.domain.Member;
import mutsa.mutsa_week5_hw.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class MutsaWeek5HwApplication {


	private final MemberRepository memberRepository;

	public static void main(String[] args) {
		SpringApplication.run(MutsaWeek5HwApplication.class, args);
	}

}
