package mutsa.w5homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.w5homework.domain.*;
import mutsa.w5homework.dto.OrderDto;
import mutsa.w5homework.repository.AddressRepository;
import mutsa.w5homework.repository.MemberRepository;
import mutsa.w5homework.repository.OrderRepository;
import mutsa.w5homework.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    //장바구니 전체 주문
    @Transactional
    public OrderDto.Response createOrderFromCart(OrderDto.CartOrderRequest requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(()
                -> new RuntimeException("Member not found"));
        Address address = addressRepository.findById(requestDto.getAddressId()).orElseThrow(()
                -> new RuntimeException("Address not found"));

        //장바구니 가져오기
        Cart cart = member.getCart();
        if(cart.getCartItems().isEmpty() || cart == null){
            throw new IllegalArgumentException("Cart is empty");
        }

        Order order = Order.builder()
                .member(member)
                .address(address)
                .orderStatus(OrderStatus.ORDERED)
                .orderDate(LocalDateTime.now())
                .build();
        for(CartItem cartItem : cart.getCartItems()){
            OrderItem orderItem = OrderItem.createOrderItem(
                    cartItem.getProduct(),
                    cartItem.getCount(),
                    cartItem.getProduct().getPrice() * cartItem.getCount()
            );
            order.addOrderItem(orderItem);
        }
        //장바구니 비우기
        cart.getCartItems().clear();

        Order savedOrder = orderRepository.save(order);
        return new OrderDto.Response(savedOrder);
    }

    @Transactional
    public OrderDto.Response createDirectOrder(OrderDto.DirectOrderRequest dto) {
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(()
                -> new RuntimeException("Member not found"));
        Address address = addressRepository.findById(dto.getAddressId()).orElseThrow(()
                -> new RuntimeException("Address not found"));
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(()
                -> new RuntimeException("Product not found"));
        Order order = Order.builder()
                .member(member)
                .address(address)
                .orderStatus(OrderStatus.ORDERED)
                .orderDate(LocalDateTime.now())
                .build();

        OrderItem orderItem = OrderItem.createOrderItem(product, dto.getCount(), product.getPrice() * dto.getCount());
        order.addOrderItem(orderItem);
        Order savedOrder = orderRepository.save(order);
        return new OrderDto.Response(savedOrder);
    }
    @Transactional
    public void cancleOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.cancle();
    }
}
