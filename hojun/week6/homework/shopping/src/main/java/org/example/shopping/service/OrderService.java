package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.*;
import org.example.shopping.dto.order.DirectOrderRequestDto;
import org.example.shopping.dto.order.OrderRequestDto;
import org.example.shopping.dto.order.OrderResponseDto;
import org.example.shopping.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponseDto createOrderFromCart(Long userId, OrderRequestDto request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(()-> new IllegalArgumentException("유효하지 않은 주소입니다."));

        if (user == null) {
            throw new IllegalStateException("해당 배송지에 연결된 유저 정보가 없습니다.");
        }

        List<CartItem> cartItems = user.getCart().getCartItems();
        if(cartItems.isEmpty()){
            throw new IllegalArgumentException("비어있는 장바구니 입니다.");
        }

        Order order = Order.createOrder(user, address);

        for(CartItem cartItem : cartItems){
            Product product = cartItem.getProduct();
            Integer quantity = cartItem.getQuantity();

            OrderItem orderItem = OrderItem.createOrderItem(order, product, quantity);
            order.getOrderItems().add(orderItem);
        }


        orderRepository.save(order);
        cartItemRepository.deleteAllInBatch(cartItems);
        return new OrderResponseDto(order);
    }

    @Transactional
    public void cancleOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
        order.cancleOrder();
    }

    @Transactional
    public OrderResponseDto directOrder(Long userId, DirectOrderRequestDto request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(()-> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(()-> new IllegalArgumentException("유효하지 않은 주소입니다."));

        Order order =  Order.createOrder(user, address);

        OrderItem orderItem = OrderItem.createOrderItem(order, product, request.getQuantity());

        order.getOrderItems().add(orderItem);

        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

}
