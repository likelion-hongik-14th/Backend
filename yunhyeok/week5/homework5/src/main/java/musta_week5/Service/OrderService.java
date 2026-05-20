package musta_week5.Service;

import lombok.RequiredArgsConstructor;
import musta_week5.Dto.OrderRequestDto;
import musta_week5.Dto.OrderResponseDto;
import musta_week5.Repository.AddressRepository;
import musta_week5.Repository.OrderRepository;
import musta_week5.domain.Address;
import musta_week5.domain.DeliverStatus;
import musta_week5.domain.Order;
import musta_week5.domain.OrderStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    public void createOrder(OrderRequestDto dto) {

        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() -> new RuntimeException("배송지 없음"));

        Order order = Order.builder()
                .address(address)
                .totalPrice(dto.getTotalPrice())
                .status(OrderStatus.ORDER_COMPLETED)
                .deliverStatus(DeliverStatus.WAITING)
                .build();
        orderRepository.save(order);
    }

    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 없음"));
        return new OrderResponseDto(
                order.getId(),order.getStatus(),
                order.getTotalPrice(), order.getDeliverStatus());
    }

    public List<OrderResponseDto> getOrders(String userId) {
        return orderRepository.findByUser_UserId(userId)
                .stream()
                .map(o -> new OrderResponseDto(o.getId(), o.getStatus(),
                       o.getTotalPrice(),o.getDeliverStatus()))
                .collect(Collectors.toList());
    }

}
