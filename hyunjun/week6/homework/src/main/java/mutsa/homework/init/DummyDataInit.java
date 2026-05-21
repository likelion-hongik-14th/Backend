package mutsa.homework.init;

import lombok.RequiredArgsConstructor;
import mutsa.homework.domain.Cart;
import mutsa.homework.domain.CartItem;
import mutsa.homework.domain.Product;
import mutsa.homework.domain.User;
import mutsa.homework.repository.CartItemRepository;
import mutsa.homework.repository.CartRepository;
import mutsa.homework.repository.ProductRepository;
import mutsa.homework.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DummyDataInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(userRepository.count() > 0) {
            return;
        }

        User user1 = User.create("kimmutsa@mutsa.com","123456789","김멋사");
        User user2 = User.create("leehongik@hongik.ac.kr","987654321","이홍익");
        User user3 = User.create("parkchulsu@naver.com","abcdefgh","박철수");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Product product1 = Product.create("키보드",30000,500);
        Product product2 = Product.create("마우스",10000,500);
        Product product3 = Product.create("모니터",200000,100);
        Product product4 = Product.create("노트북",1000000,30);
        Product product5 = Product.create("한정판 염버니 인형",99999,5);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);

        Cart cart = Cart.create(user1);
        cartRepository.save(cart);

        CartItem cartItem1 = CartItem.create(cart, product1, 2);
        CartItem cartItem2 = CartItem.create(cart, product2, 3);
        cartItemRepository.save(cartItem1);
        cartItemRepository.save(cartItem2);

        System.out.println("[안내] 테스트용 더미 데이터가 생성되었습니다.");
    }
}
