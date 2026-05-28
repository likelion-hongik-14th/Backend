package com.app.mutsa_shoppingmall;

import com.app.mutsa_shoppingmall.Entity.Product;
import com.app.mutsa_shoppingmall.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DummyDataLoader implements CommandLineRunner {
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // 데이터가 이미 있는지 확인 (서버 껐다 켤 때마다 중복으로 들어가는 것 방지)
        if (productRepository.count() == 0) {
            Product product1 = Product.builder()
                    .name("맥북 프로 16인치")
                    .price(3500000)
                    .stock(10)
                    .description("최신형 맥북입니다.")
                    .build();

            Product product2 = Product.builder()
                    .name("아이폰 15 프로")
                    .price(1550000)
                    .stock(50)
                    .description("티타늄 아이폰입니다.")
                    .build();

            Product product3 = Product.builder()
                    .name("에어팟 프로 2세대")
                    .price(350000)
                    .stock(100)
                    .description("노이즈 캔슬링 무선 이어폰")
                    .build();

            // DB에 저장!
            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);

            System.out.println(" 상품 더미 데이터 3개가 성공적으로 DB에 들어갔습니다!");
        }
    }
}
