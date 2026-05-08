package mutsa.homework.dto.cart;

import java.util.List;

public record CartResponseDto(
        List<CartItemResponseDto> items,
        int totalPrice
) {}
