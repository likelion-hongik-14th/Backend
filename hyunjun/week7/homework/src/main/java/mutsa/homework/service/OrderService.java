package mutsa.homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.homework.domain.*;
import mutsa.homework.dto.order.AddOrderRequestDto;
import mutsa.homework.dto.order.CartOrderRequestDto;
import mutsa.homework.dto.order.OrderResponseDto;
import mutsa.homework.global.apiPayload.code.*;
import mutsa.homework.global.apiPayload.exception.ProjectException;
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
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        Address address = addressRepository.findById(requestDto.addressId())
                .orElseThrow(() -> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        address.validateUser(userId);

        Product product = productRepository.findByIdWithPessimisticLock(requestDto.productId())
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        Order newOrder = Order.create(user, address);

        OrderItem newOrderItem = OrderItem.create(newOrder, product, product.getPrice(), requestDto.quantity());

        orderRepository.save(newOrder);

        return OrderResponseDto.from(newOrder);
    }

    @Transactional
    public OrderResponseDto addCartItemToOrder(Long userId, CartOrderRequestDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        Address address = addressRepository.findById(requestDto.addressId())
                .orElseThrow(() -> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        address.validateUser(userId);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(()-> new ProjectException(CartErrorCode.CART_NOT_FOUND));

        List<CartItem> cartItems = cartItemRepository.findAllByIdInAndCart(requestDto.cartItemIds(), cart);

        if (cartItems.size() != requestDto.cartItemIds().size()){
            throw new ProjectException(CartErrorCode.INVALID_CART_ITEM);
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

        userRepository.findById(userId)
                .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        List<OrderResponseDto> orders = orderRepository.findAllWithItemsByUserId(userId).stream()
                .map(OrderResponseDto::from)
                .toList();

        return ListResponseDto.of(orders);
    }

    @Transactional
    public void cancelOrder(Long userId, Long orderId){

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new ProjectException(OrderErrorCode.ORDER_NOT_FOUND));

        order.validateUser(userId);

        order.cancelOrder();
    }
}