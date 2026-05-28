package com.app.mutsa_shoppingmall.Service;

import com.app.mutsa_shoppingmall.DTO.OrderDto;
import com.app.mutsa_shoppingmall.Entity.*;
import com.app.mutsa_shoppingmall.Repository.CartRepository;
import com.app.mutsa_shoppingmall.Repository.OrderRepository;
import com.app.mutsa_shoppingmall.Repository.ProductRepository;
import com.app.mutsa_shoppingmall.exception.ErrorCode;
import com.app.mutsa_shoppingmall.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // 1. 장바구니 전체 주문
    @Transactional
    public OrderDto.Response orderFromCart() {
        Cart cart = cartRepository.findById(1L)
                .orElseThrow(() -> new GeneralException(ErrorCode.CART_NOT_FOUND));

        if (cart.getCartItems().isEmpty()) {
            throw new GeneralException(ErrorCode.CART_EMPTY);
        }

        Order order = Order.builder()
                .orderedAt(LocalDateTime.now())
                .build();

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            product.decreaseStock(cartItem.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .productName(product.getName())
                    .price(product.getPrice())
                    .quantity(cartItem.getQuantity())
                    .build();

            order.getOrderItems().add(orderItem);
        }

        cart.getCartItems().clear();

        return OrderDto.Response.from(orderRepository.save(order));
    }

    // 2. 즉시 주문
    @Transactional
    public OrderDto.Response orderDirect(OrderDto.CreateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new GeneralException(ErrorCode.PRODUCT_NOT_FOUND));

        product.decreaseStock(request.getQuantity());

        Order order = Order.builder()
                .orderedAt(LocalDateTime.now())
                .build();

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .productName(product.getName())
                .price(product.getPrice())
                .quantity(request.getQuantity())
                .build();

        order.getOrderItems().add(orderItem);

        return OrderDto.Response.from(orderRepository.save(order));
    }

    // 3. 주문 취소
    @Transactional
    public OrderDto.Response cancelOrder(Long orderId) {
        Order order = orderRepository.findByIdWithItems(orderId)
                .orElseThrow(() -> new GeneralException(ErrorCode.ORDER_NOT_FOUND));

        order.cancel();

        return OrderDto.Response.from(order);
    }

    // 4. 주문 단건 조회
    public OrderDto.Response getOrder(Long orderId) {
        Order order = orderRepository.findByIdWithItems(orderId)
                .orElseThrow(() -> new GeneralException(ErrorCode.ORDER_NOT_FOUND));

        return OrderDto.Response.from(order);
    }
}