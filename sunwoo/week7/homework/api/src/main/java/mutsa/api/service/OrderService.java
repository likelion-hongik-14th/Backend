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
import mutsa.api.enums.OrderStatus;
import mutsa.api.global.apiPayload.code.GeneralErrorCode;
import mutsa.api.global.apiPayload.exception.ProjectException;
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
            throw new ProjectException(GeneralErrorCode.CART_EMPTY);
        }

        Orders orders = Orders.create(users, address);
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            validateProductStock(product, cartItem.getQuantity());
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
        Product product = getProduct(requestDto.getProductId());

        validateProductStock(product, requestDto.getQuantity());
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
        getUsers(userId);
        Orders orders = getOrders(userId, orderId);
        return OrderResponseDto.from(orders);
    }

    @Transactional
    public OrderResponseDto cancelOrder(Long userId, Long orderId) {
        getUsers(userId);
        Orders orders = getOrders(userId, orderId);
        validateCancelable(orders);
        orders.cancel();
        return OrderResponseDto.from(orders);
    }

    @Transactional
    public OrderResponseDto completePayment(Long userId, Long orderId) {
        getUsers(userId);
        Orders orders = getOrders(userId, orderId);
        orders.completePayment();
        return OrderResponseDto.from(orders);
    }

    @Transactional
    public OrderResponseDto completeDelivery(Long userId, Long orderId) {
        getUsers(userId);
        Orders orders = getOrders(userId, orderId);
        orders.completeDelivery();
        return OrderResponseDto.from(orders);
    }

    private Users getUsers(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.USER_NOT_FOUND));
    }

    private Address getAddress(Long userId, Long addressId) {
        return addressRepository.findByIdAndUsersId(addressId, userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.ADDRESS_NOT_FOUND));
    }

    private Orders getOrders(Long userId, Long orderId) {
        return ordersRepository.findByIdAndUsersId(orderId, userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.ORDER_NOT_FOUND));
    }

    private Cart getCart(Long userId) {
        return cartRepository.findByUsersId(userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.CART_NOT_FOUND));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.PRODUCT_NOT_FOUND));
    }

    private void validateProductStock(Product product, int quantity) {
        if (product.getStock() < quantity) {
            throw new ProjectException(GeneralErrorCode.PRODUCT_OUT_OF_STOCK);
        }
    }

    private void validateCancelable(Orders orders) {
        if (orders.getStatus() == OrderStatus.DELIVERY_COMPLETED) {
            throw new ProjectException(GeneralErrorCode.ORDER_DELIVERY_COMPLETED);
        }
        if (orders.getStatus() == OrderStatus.CANCELED) {
            throw new ProjectException(GeneralErrorCode.ORDER_ALREADY_CANCELED);
        }
    }
}
