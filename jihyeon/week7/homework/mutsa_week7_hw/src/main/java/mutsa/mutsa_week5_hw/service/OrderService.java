package mutsa.mutsa_week5_hw.service;

import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.domain.*;
import mutsa.mutsa_week5_hw.dto.OrderDirectRequestDto;
import mutsa.mutsa_week5_hw.dto.OrderFromCartRequestDto;
import mutsa.mutsa_week5_hw.dto.OrderResponseDto;
import mutsa.mutsa_week5_hw.global.code.GeneralCode;
import mutsa.mutsa_week5_hw.global.exception.GeneralException;
import mutsa.mutsa_week5_hw.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;

    // 즉시 구매
    @Transactional
    public OrderResponseDto createDirectOrder(Long memberId,
                                              OrderDirectRequestDto dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new GeneralException(GeneralCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() ->
                        new GeneralException(GeneralCode.PRODUCT_NOT_FOUND));

        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() ->
                        new GeneralException(GeneralCode.ADDRESS_NOT_FOUND));

        Order order = Order.createOrder(member, address);

        OrderItem orderItem =
                OrderItem.createOrderItem(product, dto.getQuantity());

        order.addOrderItem(orderItem);

        Order savedOrder = orderRepository.save(order);

        return OrderResponseDto.from(savedOrder);
    }


    // 장바구니 주문
    @Transactional
    public OrderResponseDto createOrderFromCart(Long memberId,
                                                OrderFromCartRequestDto dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new GeneralException(GeneralCode.MEMBER_NOT_FOUND));

        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() ->
                        new GeneralException(GeneralCode.CART_NOT_FOUND));

        if (cart.getCartItems().isEmpty()) {
            throw new GeneralException(GeneralCode.CART_EMPTY);
        }

        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() ->
                        new GeneralException(GeneralCode.ADDRESS_NOT_FOUND));

        Order order = Order.createOrder(member, address);

        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem =
                    OrderItem.createOrderItem(
                            cartItem.getProduct(),
                            cartItem.getQuantity()
                    );

            order.addOrderItem(orderItem);
        }

        Order savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();

        return OrderResponseDto.from(savedOrder);
    }

    // 결제 완료 처리
    @Transactional
    public OrderResponseDto payOrder(Long memberId, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new GeneralException(GeneralCode.ORDER_NOT_FOUND));

        // 공통 검증 메서드 호출
        validateOrderOwnership(order, memberId);

        order.pay();

        return OrderResponseDto.from(order);
    }

    // 배송 완료 처리
    @Transactional
    public OrderResponseDto deliverOrder(Long memberId, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new GeneralException(GeneralCode.ORDER_NOT_FOUND));

        // 공통 검증 메서드 호출
        validateOrderOwnership(order, memberId);

        order.deliver();

        return OrderResponseDto.from(order);
    }

    // 주문 목록 조회
    public List<OrderResponseDto> getOrders(Long memberId) {

        return orderRepository.findByMemberId(memberId)
                .stream()
                .map(OrderResponseDto::from)
                .toList();
    }


    // 주문 단건 조회
    public OrderResponseDto getOrder(Long memberId, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new GeneralException(GeneralCode.ORDER_NOT_FOUND));

        // 공통 검증 메서드 호출
        validateOrderOwnership(order, memberId);

        return OrderResponseDto.from(order);
    }


    // 주문 취소
    @Transactional
    public void cancelOrder(Long memberId, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new GeneralException(GeneralCode.ORDER_NOT_FOUND));

        // 공통 검증 메서드 호출
        validateOrderOwnership(order, memberId);

        order.cancel();
    }

    /**
     * 주문 소유자 검증 공통 메서드
     */
    private void validateOrderOwnership(Order order, Long memberId) {
        if (!order.getMember().getId().equals(memberId)) {
            throw new GeneralException(GeneralCode.ORDER_FORBIDDEN);
        }
    }
}