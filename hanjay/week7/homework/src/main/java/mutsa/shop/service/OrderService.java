package mutsa.shop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mutsa.shop.domain.*;
import mutsa.shop.dto.OrderResponseDto;
import mutsa.shop.global.apiPayload.exeption.*;
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
                .orElseThrow(() -> new ProjectException(MemberErrorCode.USER_NOT_FOUND));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        List<OrderItem> orderItems = new ArrayList<>();

        // 선택한 장바구니 아이템들을 돌면서 주문 상품(OrderItem) 생성
        for (Long cartItemId : cartItemIds) {
            CartItem cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new ProjectException(CartErrorCode.CART_ITEM_NOT_FOUND));

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
                .orElseThrow(() -> new ProjectException(MemberErrorCode.USER_NOT_FOUND));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

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
                .orElseThrow(() -> new ProjectException(OrderErrorCode.ORDER_NOT_FOUND));

        // 비즈니스 로직 호출 (주문 취소 및 재고 원복)
        order.cancel();
    }

}
