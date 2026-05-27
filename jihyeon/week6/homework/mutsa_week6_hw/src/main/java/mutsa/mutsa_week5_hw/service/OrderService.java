package mutsa.mutsa_week5_hw.service;

import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.domain.*;
import mutsa.mutsa_week5_hw.dto.OrderDirectRequestDto;
import mutsa.mutsa_week5_hw.dto.OrderFromCartRequestDto;
import mutsa.mutsa_week5_hw.dto.OrderResponseDto;
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
                        new IllegalArgumentException("회원이 존재하지 않습니다.")
                );

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() ->
                        new IllegalArgumentException("상품이 존재하지 않습니다.")
                );

        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() ->
                        new IllegalArgumentException("배송지가 존재하지 않습니다.")
                );

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
                        new IllegalArgumentException("회원이 존재하지 않습니다.")
                );

        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException("장바구니가 존재하지 않습니다.")
                );

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("장바구니에 상품이 없습니다.");
        }

        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() ->
                        new IllegalArgumentException("배송지가 존재하지 않습니다.")
                );

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
                        new IllegalArgumentException("주문이 존재하지 않습니다.")
                );

        if (!order.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 회원의 주문이 아닙니다.");
        }

        order.pay();

        return OrderResponseDto.from(order);
    }

    // 배송 완료 처리
    @Transactional
    public OrderResponseDto deliverOrder(Long memberId, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException("주문이 존재하지 않습니다.")
                );

        if (!order.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 회원의 주문이 아닙니다.");
        }

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
                        new IllegalArgumentException("주문이 존재하지 않습니다.")
                );

        if (!order.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 회원의 주문이 아닙니다.");
        }

        return OrderResponseDto.from(order);
    }


    // 주문 취소
    @Transactional
    public void cancelOrder(Long memberId, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException("주문이 존재하지 않습니다.")
                );

        if (!order.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 회원의 주문이 아닙니다.");
        }

        order.cancel();
    }
}
