package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.*;
import mutsa.api.dto.OrderRequestDto;
import mutsa.api.dto.OrderResponseDto;
import mutsa.api.global.apiPayload.code.*;
import mutsa.api.global.apiPayload.exception.ProjectException;
import mutsa.api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;

    // [생성] 단일 상품 바로 주문하기
    @Transactional
    public Long createOrder(Long userId, OrderRequestDto requestDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        Address address = addressRepository.findById(requestDto.getAddressId())
                .orElseThrow(() -> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        if (!address.getUser().getId().equals(userId)){
            throw new ProjectException(GeneralErrorCode.FORBIDDEN);
        }

        OrderItem orderItem = OrderItem.createOrderItem(product, product.getPrice(), requestDto.getCount());

        Order order = Order.createOrder(user, address, List.of(orderItem));
        orderRepository.save(order);

        return order.getId();
    }

    // [생성] 장바구니 전체 품목 주문
    @Transactional
    public Long createOrderFromCart(Long userId, Long addressId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()->new ProjectException(OrderErrorCode.CART_EMPTY));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        if (!address.getUser().getId().equals(userId)) {
            throw new ProjectException(GeneralErrorCode.FORBIDDEN);
        }

        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> OrderItem.createOrderItem(
                        cartItem.getProduct(),
                        cartItem.getProduct().getPrice(),
                        cartItem.getQuantity()
                ))
                .toList();

        Order order = Order.createOrder(user, address, orderItems);
        orderRepository.save(order);

        cart.clearCart();

        return order.getId();
    }

    // [취소] 주문 취소
    @Transactional
    public void cancelOrder(Long orderId, Long userId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ProjectException(OrderErrorCode.ORDER_NOT_FOUND));

        if (!order.getUser().getId().equals(userId)) {
            throw new ProjectException(GeneralErrorCode.FORBIDDEN);
        }

        order.cancel();
    }

    // [조회] 내 주문 내역 조회
    public List<OrderResponseDto> getMyOrders(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));
        return orderRepository.findAllByUserOrderByOrderDateDesc(user).stream()
                .map(OrderResponseDto::of)
                .toList();
    }

    // [상태 변경] 배송 완료 처리
    @Transactional
    public void completeDelivery(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ProjectException(OrderErrorCode.ORDER_NOT_FOUND));

        if (!order.getUser().getId().equals(userId)) {
            throw new ProjectException(GeneralErrorCode.FORBIDDEN);
        }

        order.completeDelivery();
    }

    // [상태 변경] 주문 결제 완료 처리
    @Transactional
    public void payOrder(Long orderId, Long userId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ProjectException(OrderErrorCode.ORDER_NOT_FOUND));

        if (!order.getUser().getId().equals(userId)){
            throw new ProjectException(GeneralErrorCode.FORBIDDEN);
        }

        order.payOrder();
    }
}