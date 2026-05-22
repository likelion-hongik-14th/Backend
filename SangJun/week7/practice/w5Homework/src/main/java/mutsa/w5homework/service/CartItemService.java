package mutsa.w5homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.w5homework.domain.Cart;
import mutsa.w5homework.domain.CartItem;
import mutsa.w5homework.domain.Product;
import mutsa.w5homework.dto.CartItemCreateRequestDto;
import mutsa.w5homework.dto.CartItemResponseDto;
import mutsa.w5homework.repository.CartItemRepository;
import mutsa.w5homework.repository.CartRepository;
import mutsa.w5homework.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Transactional
    public CartItemResponseDto createCartItem(CartItemCreateRequestDto requestDto) {
        //[초기 검증] 스탁이 모자라거나 상품 ID가 없다면 예외처리
        if (requestDto.getCount() <= 0 || requestDto.getProductId() == null) {
            throw new IllegalArgumentException("Invalid request");
        }

        Cart cart = cartRepository.findByMemberId(requestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<CartItem> existingCartItemOpt = cartItemRepository.findByCartIdAndProductId(
                cart.getId(),
                product.getId());

        //객체가 존재하는지 검증 후 꺼냄
        if (existingCartItemOpt.isPresent()) {
            CartItem existingCartItem = existingCartItemOpt.get();
            //기존 장바구니 수량 + 추가 수량이 스탁을 초과하는 경우
            if(product.getStock() < requestDto.getCount() + existingCartItem.getCount()){
                throw new IllegalArgumentException("Not enough stock");
            }
            existingCartItem.addCount(requestDto.getCount());
            return new CartItemResponseDto(existingCartItem);
        }else {
            if(product.getStock() < requestDto.getCount()){
                throw new IllegalArgumentException("Not enough stock");
            }

            CartItem cartItem = CartItem.builder()
                    .count(requestDto.getCount())
                    .product(product)
                    .cart(cart)
                    .build();
            CartItem cartItemSaved = cartItemRepository.save(cartItem);
            return new  CartItemResponseDto(cartItemSaved);
        }
    }
}
