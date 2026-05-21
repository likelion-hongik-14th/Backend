package mutsa.homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.homework.domain.*;
import mutsa.homework.dto.order.AddOrderRequestDto;
import mutsa.homework.dto.order.CartOrderRequestDto;
import mutsa.homework.dto.order.OrderResponseDto;
import mutsa.homework.global.dto.ListResponseDto;
import mutsa.homework.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Transactional
    public OrderResponseDto addProductToOrder(Long userId, AddOrderRequestDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Address address = addressRepository.findById(requestDto.addressId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 주소지입니다."));

        address.validateUser(userId);

        Product product = productRepository.findById(requestDto.productId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        Order newOrder = Order.create(user, address);

        OrderItem newOrderItem = OrderItem.create(newOrder, product, product.getPrice(), requestDto.quantity());

        orderRepository.save(newOrder);

        return OrderResponseDto.from(newOrder);
    }

    @Transactional
    public OrderResponseDto addCartItemToOrder(Long userId, CartOrderRequestDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Address address = addressRepository.findById(requestDto.addressId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 주소지입니다."));

        address.validateUser(userId);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(()-> new IllegalArgumentException("장바구니가 존재하지 않습니다."));

        List<CartItem> cartItems = cartItemRepository.findAllByIdInAndCart(requestDto.cartItemIds(), cart);

        if (cartItems.size() != requestDto.cartItemIds().size()){
            throw new IllegalArgumentException("유효하지 않은 장바구니 상품이 포함되어 있습니다.");
        }

        Order newOrder = Order.create(user, address);

        for (CartItem cartItem : cartItems) {
            OrderItem.create(
                    newOrder,
                    cartItem.getProduct(),
                    cartItem.getProduct().getPrice(),
                    cartItem.getQuantity()
            );
        }

        orderRepository.save(newOrder);
        cartItemRepository.deleteAll(cartItems);

        return OrderResponseDto.from(newOrder);
    }

    public ListResponseDto<OrderResponseDto> getOrder(Long userId){

        List<OrderResponseDto> orders = orderRepository.findAllWithItemsByUserId(userId).stream()
                .map(OrderResponseDto::from)
                .toList();

        return ListResponseDto.of(orders);
    }

    @Transactional
    public void cancelOrder(Long userId, Long orderId){

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new IllegalArgumentException("잘못된 접근입니다. (주문 조회 불가)"));

        order.validateUser(userId);

        order.cancelOrder();
    }
}