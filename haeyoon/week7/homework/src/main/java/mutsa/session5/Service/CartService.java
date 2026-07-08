package mutsa.session5.Service;

import mutsa.session5.Dto.CartItemRequestDto;
import mutsa.session5.Dto.CartItemResponseDto;
import mutsa.session5.Dto.CartResponseDto;
import mutsa.session5.Entity.Cart;
import mutsa.session5.Entity.CartItem;
import mutsa.session5.Entity.Member;
import mutsa.session5.Entity.Product;
import mutsa.session5.Repository.MemberRepository;
import mutsa.session5.global.apipayload.exception.CartException;
import mutsa.session5.global.apipayload.exception.MemberException;
import mutsa.session5.global.apipayload.exception.ProductException;
import mutsa.session5.global.apipayload.exception.code.CartErrorCode;
import mutsa.session5.global.apipayload.exception.code.MemberErrorCode;
import mutsa.session5.global.apipayload.exception.code.ProductErrorCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import mutsa.session5.Repository.CartItemRepository;
import mutsa.session5.Repository.CartRepository;
import mutsa.session5.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;

    // 장바구니 상품 담기
    @Transactional
    public CartItemResponseDto addCartItem(CartItemRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));
        // 장바구니 조회 및 생성
        Cart cart = cartRepository.findByMember_MemberId(requestDto.getMemberId())
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .member(member)
                        .build()));

        // 재고 확인
        int currentQuantity = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(requestDto.getProductId()))
                .mapToInt(CartItem::getQuantity)
                .sum();

        int totalQuantity = currentQuantity + requestDto.getQuantity();

        if (totalQuantity <= 0) {
            throw new CartException(CartErrorCode.INVALID_QUANTITY);
        }
        if (totalQuantity > product.getStock()) {
            throw new CartException(CartErrorCode.OUT_OF_STOCK);
        }

        // 기존 상품 중복 확인
        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);

        CartItem savedItem;
        if (existingItem.isPresent()) {
            savedItem = existingItem.get();
            savedItem.increaseQuantity(requestDto.getQuantity());
        }
        else {
            savedItem = cart.addProduct(product, requestDto.getQuantity());
        }

        return CartItemResponseDto.from(savedItem);
    }

    // 장바구니 상품 조회
    @Transactional(readOnly = true)
    public CartResponseDto getCartResponseDto(Long memberId) {
        Cart cart = cartRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new CartException(CartErrorCode.CART_NOT_FOUND));

        return CartResponseDto.from(cart);
    }

    // 장바구니 상품 수량 변경
    @Transactional
    public CartItemResponseDto updateCartItemQuantity(Long memberId, Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository
                .findByCartItemIdAndCart_Member_MemberId(cartItemId, memberId)
                .orElseThrow(() -> new CartException(CartErrorCode.CART_ITEM_NOT_FOUND));

        // 재고 확인
        if (cartItem.getProduct().getStock() < quantity) {
            throw new CartException(CartErrorCode.OUT_OF_STOCK);
        }

        cartItem.updateQuantity(quantity);

        return CartItemResponseDto.from(cartItem);
    }

    // 장바구니 상품 삭제
    @Transactional
    public void deleteCartItem(Long memberId, Long cartItemId) {
        CartItem cartItem = cartItemRepository
                .findByCartItemIdAndCart_Member_MemberId(cartItemId, memberId)
                .orElseThrow(() -> new CartException(CartErrorCode.CART_ITEM_NOT_FOUND));

        cartItemRepository.delete(cartItem);
    }
}