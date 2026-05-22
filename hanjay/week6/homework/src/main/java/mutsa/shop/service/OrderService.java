package mutsa.shop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mutsa.shop.domain.*;
import mutsa.shop.dto.OrderResponseDto;
import mutsa.shop.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public OrderResponseDto createOrderFromCart(Long memberId, Long addressId, List<Long> cartItemIds) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. ID: " + memberId));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 배송지입니다. ID: " + addressId));

        List<OrderItem> orderItems = new ArrayList<>();

        // 선택한 장바구니 아이템들을 돌면서 주문 상품(OrderItem) 생성
        for (Long cartItemId : cartItemIds) {
            CartItem cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new IllegalArgumentException("장바구니 아이템을 찾을 수 없습니다. ID: " + cartItemId));

            Product product = cartItem.getProduct();

            // 재고 차감 및 주문 상품 생성
            product.removeStock(cartItem.getQuantity());
            OrderItem orderItem = OrderItem.createOrderItem(product, cartItem.getQuantity());
            orderItems.add(orderItem);
        }

        // 주문(Order) 생성 및 저장
        Order order = Order.createOrder(member, address, orderItems);
        Order savedOrder = orderRepository.save(order);


        cartItemRepository.deleteAllById(cartItemIds);

        return OrderResponseDto.from(savedOrder);
    }

    @Transactional
    public OrderResponseDto createOrderDirect(Long memberId, Long addressId, Long productId, Long quantity) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. ID: " + memberId));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 배송지입니다. ID: " + addressId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다. ID: " + productId));

        List<OrderItem> orderItems = new ArrayList<>();

        // 재고 차감 및 주문 상품 생성
        product.removeStock(quantity);
        OrderItem orderItem = OrderItem.createOrderItem(product, quantity);
        orderItems.add(orderItem);

        // 주문 생성 및 저장
        Order order = Order.createOrder(member, address, orderItems);
        Order savedOrder = orderRepository.save(order);

        return OrderResponseDto.from(savedOrder);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다. ID: " + orderId));

        // 비즈니스 로직 호출 (주문 취소 및 재고 원복)
        order.cancel();
    }

}
