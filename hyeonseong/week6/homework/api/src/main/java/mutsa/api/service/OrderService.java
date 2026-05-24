package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.*;
import mutsa.api.dto.OrderRequestDto;
import mutsa.api.dto.OrderResponseDto;
import mutsa.api.global.apiPayload.code.AddressErrorCode;
import mutsa.api.global.apiPayload.code.OrderErrorCode;
import mutsa.api.global.apiPayload.code.ProductErrorCode;
import mutsa.api.global.apiPayload.code.UserErrorCode;
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

    private User getTestUser(){
        return userRepository.findById(1L).orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));
    }

    // [생성] 단일 상품 바로 주문하기
    @Transactional
    public Long createOrder(OrderRequestDto requestDto){
        User user = getTestUser();

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));
        Address address = addressRepository.findById(requestDto.getAddressId())
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        OrderItem orderItem = OrderItem.createOrderItem(product, product.getPrice(), requestDto.getCount());

        Order order = Order.createOrder(user, address, List.of(orderItem));
        orderRepository.save(order);

        return order.getId();
    }

    // [생성] 장바구니 전체 품목 주문
    @Transactional
    public Long createOrderFromCart(Long addressId){
        User user = getTestUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()->new ProjectException(OrderErrorCode.CART_EMPTY));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

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
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ProjectException(OrderErrorCode.ORDER_NOT_FOUND));

        order.cancel();

        for (OrderItem orderItem : order.getOrderItems()){
            Product product = orderItem.getProduct();
            product.addStock(orderItem.getCount());
        }
    }

    // [조회] 내 주문 내역 조회
    public List<OrderResponseDto> getMyOrders(){
        User user = getTestUser();
        return orderRepository.findAllByUserOrderByOrderDateDesc(user).stream()
                .map(OrderResponseDto::of)
                .toList();
    }

    // [상태 변경] 배송 완료 처리
    @Transactional
    public void completeDelivery(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ProjectException(OrderErrorCode.ORDER_NOT_FOUND));

        order.completeDelivery();
    }
}