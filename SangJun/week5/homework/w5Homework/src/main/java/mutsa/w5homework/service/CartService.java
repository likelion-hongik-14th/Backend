package mutsa.w5homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.w5homework.domain.Cart;
import mutsa.w5homework.dto.CartResponseDto;
import mutsa.w5homework.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    @Transactional(readOnly = true)
    public CartResponseDto getCart(Long memberId) {
        Cart cart = cartRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return new CartResponseDto(cart);
    }
}
