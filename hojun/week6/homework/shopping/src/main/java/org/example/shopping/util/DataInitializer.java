package org.example.shopping.util;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.*;
import org.example.shopping.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public void run(String... args){

        if (userRepository.count() > 0) {
            return;
        }

        User user1 = User.builder().name("김철수").email("chulsoo@gmail.com").password("password1234").build();
        User user2 = User.builder().name("이영희").email("younghee@gmail.com").password("password1234").build();
        userRepository.save(user1);
        userRepository.save(user2);

        Address addr1 = Address.builder().address("서울시 강남구 테헤란로 123").zipcode("06234").phone("010-1111-2222").name("우리집").user(user1).build();
        Address addr2 = Address.builder().address("서울시 서초구 반포대로 456").zipcode("06678").phone("010-1111-3333").name("회사").user(user1).build();
        Address addr3 = Address.builder().address("부산시 해운대구 해운대로 789").zipcode("48123").phone("010-4444-5555").name("부산 본가").user(user2).build();
        Address addr4 = Address.builder().address("경기도 수원시 팔달구 권광로 99").zipcode("16456").phone("010-4444-6666").name("자취방").user(user2).build();
        addressRepository.save(addr1);
        addressRepository.save(addr2);
        addressRepository.save(addr3);
        addressRepository.save(addr4);

        Product prod1 = Product.builder().name("러닝화").price(200000).stock(50).build();
        Product prod2 = Product.builder().name("자전거").price(400000).stock(30).build();
        productRepository.save(prod1);
        productRepository.save(prod2);

        Cart cart1 = user1.getCart();

        CartItem cartItem1 = CartItem.builder()
                .cart(cart1)
                .product(prod1)
                .quantity(2)
                .build();

        CartItem cartItem2 = CartItem.builder()
                .cart(cart1)
                .product(prod2)
                .quantity(5)
                .build();

        cartItemRepository.save(cartItem1);
        cartItemRepository.save(cartItem2);
    }
}
