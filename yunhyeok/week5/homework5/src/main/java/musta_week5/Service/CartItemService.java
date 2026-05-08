package musta_week5.Service;

import lombok.RequiredArgsConstructor;
import musta_week5.Dto.CartItemRequestDto;
import musta_week5.Dto.CartItemResponseDto;
import musta_week5.Repository.CartItemRepository;
import musta_week5.Repository.CartRepository;
import musta_week5.domain.Cart;
import musta_week5.domain.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;  // ← CartRepository로 수정!

    // 장바구니 담기
    public void addItem(Long cartId, CartItemRequestDto dto) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("장바구니 없음"));
        CartItem item = CartItem.builder()
                .cart(cart)
                .quantity(dto.getQuantity())
                .build();
        cartItemRepository.save(item);
    }

    public List<CartItemResponseDto> getItems(Long cartId) {
        return cartItemRepository.findByCart_Id(cartId)
                .stream()
                .map(i -> new CartItemResponseDto(
                        i.getId(),
                        i.getOption().getId(),
                        i.getQuantity()))
                .collect(Collectors.toList());
    }

    // 장바구니 삭제
    public void deleteItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }
}