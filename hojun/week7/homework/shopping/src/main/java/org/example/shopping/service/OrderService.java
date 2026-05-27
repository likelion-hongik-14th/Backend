package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.*;
import org.example.shopping.domain.enums.OrderStatus;
import org.example.shopping.dto.order.DirectOrderRequestDto;
import org.example.shopping.dto.order.OrderRequestDto;
import org.example.shopping.dto.order.OrderResponseDto;
import org.example.shopping.global.apiPayload.code.domain.*;
import org.example.shopping.global.apiPayload.exception.ProjectException;
import org.example.shopping.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        Address address = addressRepository.findByIdAndUserId(userId, request.getAddressId())
                .orElseThrow(()-> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        List<CartItem> cartItems = user.getCart().getCartItems();
        if(cartItems.isEmpty()){
            throw new ProjectException(CartErrorCode.EMPTY_CART);
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
    public void cancelOrder(Long userId, Long orderId){
        Order order = orderRepository.findByIdAndUserId(userId, orderId)
                .orElseThrow(()-> new ProjectException(OrderErrorCode.ORDER_NOT_FOUND));
        if(order.getOrderStatus() == OrderStatus.CANCEL){
            throw new ProjectException(OrderErrorCode.CANNOT_CANCEL);
        }
        order.cancleOrder();
    }

    @Transactional
    public OrderResponseDto directOrder(Long userId, DirectOrderRequestDto request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(()-> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(()-> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        Order order =  Order.createOrder(user, address);

        OrderItem orderItem = OrderItem.createOrderItem(order, product, request.getQuantity());

        order.getOrderItems().add(orderItem);

        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

}
