package mutsa.week2.cart;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mutsa.week2.global.apiPayload.exception.BusinessException;
import mutsa.week2.member.Member;
import mutsa.week2.member.MemberService;
import mutsa.week2.product.Product;
import mutsa.week2.product.ProductErrorCode;
import mutsa.week2.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberService memberService;

    @Transactional
    public CartItemResponseDto addItem(Long memberId, CartAddItemRequestDto requestDto) {
        Cart cart = getOrCreateCart(memberId);
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new BusinessException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (cartItemRepository.existsByCart_IdAndProduct_Id(cart.getId(), product.getId())) {
            throw new BusinessException(CartErrorCode.CART_ITEM_ALREADY_EXISTS);
        }

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(requestDto.getQuantity())
                .build();
        cart.addItem(cartItem);

        return CartItemResponseDto.from(cartItemRepository.save(cartItem));
    }

    public CartResponseDto getCart(Long memberId) {
        List<CartItemResponseDto> cartItems = cartItemRepository.findByCart_Member_Id(memberId).stream()
                .map(CartItemResponseDto::from)
                .toList();
        return CartResponseDto.of(cartItems);
    }

    @Transactional
    public CartItemResponseDto updateQuantity(Long memberId, Long itemId,
                                              CartUpdateQuantityRequestDto requestDto) {
        CartItem cartItem = findOwnedCartItem(memberId, itemId);
        cartItem.updateQuantity(requestDto.getQuantity());
        return CartItemResponseDto.from(cartItem);
    }

    @Transactional
    public void deleteItem(Long memberId, Long itemId) {
        CartItem cartItem = findOwnedCartItem(memberId, itemId);
        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public CartItemResponseDto updateImage(Long memberId, Long itemId,
                                           CartUpdateImageRequestDto requestDto) {
        CartItem cartItem = findOwnedCartItem(memberId, itemId);
        cartItem.updateImageUrl(requestDto.getImageUrl());
        return CartItemResponseDto.from(cartItem);
    }

    @Transactional
    public Cart getOrCreateCart(Long memberId) {
        return cartRepository.findByMember_Id(memberId)
                .orElseGet(() -> {
                    Member member = memberService.getMember(memberId);
                    return cartRepository.save(Cart.builder().member(member).build());
                });
    }

    private CartItem findOwnedCartItem(Long memberId, Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(CartErrorCode.CART_ITEM_NOT_FOUND));
        if (!cartItem.getCart().getMember().getId().equals(memberId)) {
            throw new BusinessException(CartErrorCode.CART_FORBIDDEN);
        }
        return cartItem;
    }
}
