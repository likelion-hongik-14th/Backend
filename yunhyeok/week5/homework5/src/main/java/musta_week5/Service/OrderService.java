package musta_week5.Service;

import lombok.RequiredArgsConstructor;
import musta_week5.Dto.OrderRequestDto;
import musta_week5.Dto.OrderResponseDto;
import musta_week5.Repository.OrderRepository;
import musta_week5.domain.Order;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository orderRepository;

    public void createOrder(OrderRequestDto dto) {
        Order order = Order.builder()
                .address(dto.getAddress())
                .totalPrice(dto.getTotalPrice())
                .status("주문완료")
                .build();
        orderRepository.save(order);
    }

    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 없음"));
        return new OrderResponseDto(
                order.getId(), order.getStatus(),
                order.getAddress(), order.getTotalPrice());
    }

    public List<OrderResponseDto> getOrders(String userId) {
        return orderRepository.findByUser_UserId(userId)
                .stream()
                .map(o -> new OrderResponseDto(o.getId(), o.getStatus(),
                        o.getAddress(), o.getTotalPrice()))
                .collect(Collectors.toList());
    }

}
