package mutsa.week2.order;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mutsa.week2.address.Address;
import mutsa.week2.address.AddressService;
import mutsa.week2.cart.Cart;
import mutsa.week2.cart.CartItem;
import mutsa.week2.cart.CartItemRepository;
import mutsa.week2.cart.CartService;
import mutsa.week2.global.apiPayload.exception.BusinessException;
import mutsa.week2.member.Member;
import mutsa.week2.member.MemberService;
import mutsa.week2.product.Product;
import mutsa.week2.product.ProductErrorCode;
import mutsa.week2.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberService memberService;
    private final AddressService addressService;
    private final CartService cartService;

    @Transactional
    public OrderResponseDto createFromCart(Long memberId, OrderCreateFromCartRequestDto requestDto) {
        Member member = memberService.getMember(memberId);
        Address address = addressService.getOwnedAddress(memberId, requestDto.getAddressId());

        List<CartItem> cartItems = cartItemRepository.findByCart_Member_Id(memberId);
        if (cartItems.isEmpty()) {
            throw new BusinessException(OrderErrorCode.EMPTY_CART);
        }

        Order order = Order.builder()
                .member(member)
                .address(address)
                .build();

        cartItems.forEach(cartItem -> order.addItem(
                OrderItem.builder()
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity())
                        .build()
        ));

        Order saved = orderRepository.save(order);

        Cart cart = cartService.getOrCreateCart(memberId);
        cart.clear();

        return OrderResponseDto.from(saved);
    }

    @Transactional
    public OrderResponseDto createFromProduct(Long memberId, OrderCreateFromProductRequestDto requestDto) {
        if (requestDto.getQuantity() == null || requestDto.getQuantity() < 1) {
            throw new BusinessException(OrderErrorCode.INVALID_QUANTITY);
        }

        Member member = memberService.getMember(memberId);
        Address address = addressService.getOwnedAddress(memberId, requestDto.getAddressId());
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new BusinessException(ProductErrorCode.PRODUCT_NOT_FOUND));

        Order order = Order.builder()
                .member(member)
                .address(address)
                .build();

        order.addItem(
                OrderItem.builder()
                        .product(product)
                        .quantity(requestDto.getQuantity())
                        .build()
        );

        return OrderResponseDto.from(orderRepository.save(order));
    }

    public OrderListResponseDto findByMember(Long memberId) {
        memberService.getMember(memberId);
        List<OrderResponseDto> orders = orderRepository.findByMember_IdOrderByOrderedAtDesc(memberId).stream()
                .map(OrderResponseDto::from)
                .toList();
        return OrderListResponseDto.of(orders);
    }

    public OrderResponseDto findById(Long memberId, Long orderId) {
        return OrderResponseDto.from(getOwnedOrder(memberId, orderId));
    }

    @Transactional
    public OrderResponseDto pay(Long memberId, Long orderId) {
        Order order = getOwnedOrder(memberId, orderId);
        order.pay();
        return OrderResponseDto.from(order);
    }

    @Transactional
    public OrderResponseDto deliver(Long memberId, Long orderId) {
        Order order = getOwnedOrder(memberId, orderId);
        order.deliver();
        return OrderResponseDto.from(order);
    }

    @Transactional
    public OrderResponseDto cancel(Long memberId, Long orderId) {
        Order order = getOwnedOrder(memberId, orderId);
        order.cancel();
        return OrderResponseDto.from(order);
    }

    private Order getOwnedOrder(Long memberId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(OrderErrorCode.ORDER_NOT_FOUND));
        if (!order.getMember().getId().equals(memberId)) {
            throw new BusinessException(OrderErrorCode.ORDER_FORBIDDEN);
        }
        return order;
    }
}
