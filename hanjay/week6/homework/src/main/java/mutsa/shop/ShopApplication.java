package mutsa.shop;

import mutsa.shop.domain.Member;
import mutsa.shop.domain.Product;
import mutsa.shop.repository.MemberRepository;
import mutsa.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

    @Bean
    public CommandLineRunner initData(MemberRepository memberRepository, ProductRepository productRepository) {
        return args -> {
            // 데이터베이스에 회원이 하나도 없을 때만 실행
            if (memberRepository.count() == 0) {

                // Member 엔티티에 다른 필드가 없으므로 빈 빌더로 생성합니다.
                Member dummyMember = Member.builder()
                        .build();

                memberRepository.save(dummyMember);
                System.out.println("=== 로컬 테스트용 더미 회원(ID: 1)이 자동으로 생성되었습니다. ===");
            }
            if (productRepository.count() == 0) {
                // 💡 본인의 Product 엔티티 필드 구조(상품명, 가격 등)에 맞게 빌더를 수정해 주세요.
                Product dummyProduct = Product.builder()
                        .name("맛있는 귤 1kg")
                        .price(5000L)
                        .stock(100L)
                        .build();

                productRepository.save(dummyProduct);
                System.out.println("=== 로컬 테스트용 더미 상품(ID: 1)이 자동으로 생성되었습니다. ===");
            }
        };
    }
}
