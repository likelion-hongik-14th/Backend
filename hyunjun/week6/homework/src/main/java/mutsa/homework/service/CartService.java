package mutsa.homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.homework.domain.Cart;
import mutsa.homework.domain.CartItem;
import mutsa.homework.domain.Product;
import mutsa.homework.domain.User;
import mutsa.homework.dto.cart.AddCartItemRequestDto;
import mutsa.homework.dto.cart.CartItemResponseDto;
import mutsa.homework.dto.cart.CartResponseDto;
import mutsa.homework.dto.cart.UpdateQuantityRequestDto;
import mutsa.homework.repository.CartItemRepository;
import mutsa.homework.repository.CartRepository;
import mutsa.homework.repository.ProductRepository;
import mutsa.homework.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public CartItemResponseDto addProductToCart(Long userId, AddCartItemRequestDto requestDto){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(()->{
                    Cart newCart = Cart.create(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(requestDto.productId())
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 상품입니다."));

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        int currentQuantity = optionalCartItem.map(CartItem::getQuantity).orElse(0);
        int totalQuantity = currentQuantity + requestDto.quantity();

        product.verifyStock(totalQuantity);

        CartItem resultItem = optionalCartItem
                .map(item -> {
                    item.increaseQuantity(requestDto.quantity());
                    return item;
                })
                .orElseGet(() -> cartItemRepository.save(CartItem.create(cart, product, requestDto.quantity())));

        return CartItemResponseDto.from(resultItem);
    }

    public CartResponseDto getCartItems(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Optional<Cart> optionalCart = cartRepository.findByUser(user);

        if (optionalCart.isEmpty()){

            return new CartResponseDto(Collections.emptyList(),0);
        }

        Cart cart = optionalCart.get();

        return CartResponseDto.from(cart);
    }

    @Transactional
    public CartItemResponseDto updateCartItemQuantity(Long userId, Long cartItemId, UpdateQuantityRequestDto requestDto){

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()-> new IllegalArgumentException("잘못된 접근입니다. (상품 조회 불가)"));

        cartItem.validateUser(userId);

        cartItem.updateQuantity(requestDto.quantity());

        return CartItemResponseDto.from(cartItem);
    }

    @Transactional
    public void deleteCartItem(Long userId, Long cartItemId){

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()-> new IllegalArgumentException("잘못된 접근입니다. (상품 조회 불가)"));

        cartItem.validateUser(userId);

        cartItemRepository.delete(cartItem);
    }

}
