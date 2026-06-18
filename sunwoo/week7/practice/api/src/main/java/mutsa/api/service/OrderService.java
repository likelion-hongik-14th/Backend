package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Address;
import mutsa.api.domain.Cart;
import mutsa.api.domain.CartItem;
import mutsa.api.domain.OrderItem;
import mutsa.api.domain.Orders;
import mutsa.api.domain.Product;
import mutsa.api.domain.Users;
import mutsa.api.dto.CartOrderRequestDto;
import mutsa.api.dto.DirectOrderRequestDto;
import mutsa.api.dto.OrderResponseDto;
import mutsa.api.global.apiPayload.exception.NotFoundException;
import mutsa.api.repository.AddressRepository;
import mutsa.api.repository.CartRepository;
import mutsa.api.repository.OrdersRepository;
import mutsa.api.repository.ProductRepository;
import mutsa.api.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Transactional
    public OrderResponseDto createOrderFromCart(Long userId, CartOrderRequestDto requestDto) {
        Users users = getUsers(userId);
        Address address = getAddress(userId, requestDto.getAddressId());
        Cart cart = getCart(userId);

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Orders orders = Orders.create(users, address);
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            OrderItem orderItem = OrderItem.create(product, cartItem.getQuantity());
            product.decreaseStock(cartItem.getQuantity());
            orders.addOrderItem(orderItem);
        }

        Orders savedOrders = ordersRepository.save(orders);
        cart.getCartItems().clear();
        return OrderResponseDto.from(savedOrders);
    }

    @Transactional
    public OrderResponseDto createDirectOrder(Long userId, DirectOrderRequestDto requestDto) {
        Users users = getUsers(userId);
        Address address = getAddress(userId, requestDto.getAddressId());
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + requestDto.getProductId()));

        OrderItem orderItem = OrderItem.create(product, requestDto.getQuantity());
        product.decreaseStock(requestDto.getQuantity());

        Orders orders = Orders.create(users, address);
        orders.addOrderItem(orderItem);

        return OrderResponseDto.from(ordersRepository.save(orders));
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrders(Long userId) {
        getUsers(userId);
        return ordersRepository.findByUsersId(userId).stream()
                .map(OrderResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrder(Long userId, Long orderId) {
        Orders orders = getOrders(userId, orderId);
        return OrderResponseDto.from(orders);
    }

    @Transactional
    public OrderResponseDto cancelOrder(Long userId, Long orderId) {
        Orders orders = getOrders(userId, orderId);
        orders.cancel();
        return OrderResponseDto.from(orders);
    }

    @Transactional
    public OrderResponseDto completePayment(Long userId, Long orderId) {
        Orders orders = getOrders(userId, orderId);
        orders.completePayment();
        return OrderResponseDto.from(orders);
    }

    @Transactional
    public OrderResponseDto completeDelivery(Long userId, Long orderId) {
        Orders orders = getOrders(userId, orderId);
        orders.completeDelivery();
        return OrderResponseDto.from(orders);
    }

    private Users getUsers(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }

    private Address getAddress(Long userId, Long addressId) {
        return addressRepository.findByIdAndUsersId(addressId, userId)
                .orElseThrow(() -> new NotFoundException("Address not found with id: " + addressId));
    }

    private Orders getOrders(Long userId, Long orderId) {
        return ordersRepository.findByIdAndUsersId(orderId, userId)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));
    }

    private Cart getCart(Long userId) {
        return cartRepository.findByUsersId(userId)
                .orElseThrow(() -> new NotFoundException("Cart not found with user id: " + userId));
    }
}
