package mutsa.session5.Service;

import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.OrderItemRequestDto;
import mutsa.session5.Dto.OrderItemResponseDto;
import mutsa.session5.Dto.OrderResponseDto;
import mutsa.session5.Entity.*;
import mutsa.session5.Repository.*;
import mutsa.session5.global.apipayload.exception.MemberException;
import mutsa.session5.global.apipayload.exception.OrderException;
import mutsa.session5.global.apipayload.exception.ProductException;
import mutsa.session5.global.apipayload.exception.code.MemberErrorCode;
import mutsa.session5.global.apipayload.exception.code.OrderErrorCode;
import mutsa.session5.global.apipayload.exception.code.ProductErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;

    // 주문 생성 및 저장
    public OrderItemResponseDto createOrder(OrderItemRequestDto requestDto) {

        // 필요한 엔티티 조회
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        Address address = addressRepository.findById(requestDto.getAddressId())
                .orElseThrow(() -> new OrderException(OrderErrorCode.ADDRESS_NOT_FOUND));

        // 재고 차감
        product.removeStock(requestDto.getOrderQuantity());

        Delivery delivery = Delivery.builder()
                .deliveryStatus(Delivery.DeliveryStatus.PREPARING)
                .addressName(address.getAddressName())
                .zipCode(address.getZipCode())
                .baseAddress(address.getBaseAddress())
                .detailAddress(address.getDetailAddress())
                .phoneNumber(address.getPhoneNumber())
                .build();

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .name(product.getName())
                .orderPrice(product.getPrice())
                .orderQuantity(requestDto.getOrderQuantity())
                .build();

        Order order = Order.builder()
                .member(member)
                .orderStatus(Order.OrderStatus.ORDERPLACED)
                .orderDate(LocalDateTime.now())
                .totalPrice(orderItem.getOrderPrice() * orderItem.getOrderQuantity())
                .build();

        order.addOrderItem(orderItem);
        order.setDelivery(delivery);

        Order savedOrder = orderRepository.save(order);

        return OrderItemResponseDto.of(savedOrder, orderItem);
    }

    // 주문 상태 관리
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderResponseDto(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        return OrderResponseDto.from(order);
    }

    // 배송 완료
    public void completeDelivery(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        order.updateStatus(Order.OrderStatus.DELIVERED);
    }

    // 결제 완료
    public void confirmPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        if (order.getOrderStatus() != Order.OrderStatus.ORDERPLACED) {
            throw new OrderException(OrderErrorCode.INVALID_ORDER_STATUS);
        }

        order.updateStatus(Order.OrderStatus.PAYMENTCONFIRMED);
    }

    // 주문 취소
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        order.updateStatus(Order.OrderStatus.ORDERCANCELED);

        order.getOrderItems().forEach(item -> {
            item.getProduct().addStock(item.getOrderQuantity());
        });
    }
}