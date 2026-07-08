package com.study.shop.service;

import com.study.shop.domain.*;
import com.study.shop.dto.order.*;
import com.study.shop.global.apiPayload.code.*;
import com.study.shop.global.apiPayload.exception.ProjectException;
import com.study.shop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public OrderResponse createCartOrder(Long memberId, CartOrderRequest request) {
        Member member = findMember(memberId);
        Address address = findAddress(memberId, request.getAddressId());

        validateDuplicateCartItemIds(request.getCartItemIds());

        List<CartItem> cartItems = request.getCartItemIds()
                .stream()
                .map(cartItemId -> cartItemRepository.findById(cartItemId)
                        .orElseThrow(() -> new ProjectException(CartErrorCode.CART_ITEM_NOT_FOUND)))
                .toList();

        for (CartItem cartItem : cartItems) {
            validateMyCartItem(memberId, cartItem);
        }

        int totalPrice = cartItems.stream()
                .mapToInt(CartItem::getItemTotalPrice)
                .sum();

        Order order = orderRepository.save(new Order(member, address, totalPrice));

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();

                    product.decreaseStock(cartItem.getQuantity());

                    return new OrderItem(
                            product,
                            order,
                            cartItem.getQuantity(),
                            product.getPrice(),
                            product.getName()
                    );
                })
                .toList();

        List<OrderItem> savedOrderItems = orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteAllInBatch(cartItems);

        return new OrderResponse(order, toOrderItemResponses(savedOrderItems));
    }

    @Transactional
    public OrderResponse createDirectOrder(Long memberId, DirectOrderRequest request) {
        Member member = findMember(memberId);
        Address address = findAddress(memberId, request.getAddressId());

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.decreaseStock(request.getQuantity());

        int totalPrice = product.getPrice() * request.getQuantity();

        Order order = orderRepository.save(new Order(member, address, totalPrice));

        OrderItem orderItem = orderItemRepository.save(new OrderItem(
                product,
                order,
                request.getQuantity(),
                product.getPrice(),
                product.getName()
        ));

        return new OrderResponse(order, List.of(new OrderItemResponse(orderItem)));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders(Long memberId) {
        findMember(memberId);

        List<Order> orders = orderRepository.findAllByMemberIdOrderByOrderedAtDesc(memberId);

        if (orders.isEmpty()) {
            return List.of();
        }

        List<Long> orderIds = orders.stream()
                .map(Order::getId)
                .toList();

        Map<Long, List<OrderItem>> orderItemMap = orderItemRepository.findAllByOrderIdIn(orderIds)
                .stream()
                .collect(Collectors.groupingBy(orderItem -> orderItem.getOrder().getId()));

        return orders.stream()
                .map(order -> {
                    List<OrderItemResponse> items = orderItemMap
                            .getOrDefault(order.getId(), Collections.emptyList())
                            .stream()
                            .map(OrderItemResponse::new)
                            .toList();

                    return new OrderResponse(order, items);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long memberId, Long orderId) {
        Order order = findOrder(memberId, orderId);

        List<OrderItemResponse> items = orderItemRepository.findAllByOrderId(order.getId())
                .stream()
                .map(OrderItemResponse::new)
                .toList();

        return new OrderResponse(order, items);
    }

    @Transactional
    public OrderStatusResponse cancelOrder(Long memberId, Long orderId) {
        Order order = findOrder(memberId, orderId);

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());

        order.cancel();

        for (OrderItem orderItem : orderItems) {
            orderItem.getProduct().increaseStock(orderItem.getQuantity());
        }

        return new OrderStatusResponse(order, "주문이 취소되었습니다.");
    }

    @Transactional
    public OrderStatusResponse updateOrderStatus(Long memberId, Long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ProjectException(OrderErrorCode.ORDER_NOT_FOUND));

        if (!order.getMember().getId().equals(memberId)) {
            throw new ProjectException(OrderErrorCode.ORDER_ACCESS_DENIED);
        }

        order.updateStatus(request.getStatus());

        return new OrderStatusResponse(order, "주문 상태가 변경되었습니다.");
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    private Address findAddress(Long memberId, Long addressId) {
        return addressRepository.findByIdAndMemberIdAndDeletedFalse(addressId, memberId)
                .orElseThrow(() -> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));
    }

    private Order findOrder(Long memberId, Long orderId) {
        return orderRepository.findByIdAndMemberId(orderId, memberId)
                .orElseThrow(() -> new ProjectException(OrderErrorCode.ORDER_NOT_FOUND));
    }

    private void validateMyCartItem(Long memberId, CartItem cartItem) {
        if (!cartItem.getCart().getMember().getId().equals(memberId)) {
            throw new ProjectException(CartErrorCode.CART_ACCESS_DENIED);
        }
    }

    private void validateDuplicateCartItemIds(List<Long> cartItemIds) {
        Set<Long> uniqueIds = new HashSet<>(cartItemIds);

        if (uniqueIds.size() != cartItemIds.size()) {
            throw new ProjectException(OrderErrorCode.DUPLICATE_CART_ITEM);
        }
    }

    private List<OrderItemResponse> toOrderItemResponses(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItemResponse::new)
                .toList();
    }
}
