package org.example.shopping.dto.order;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderRequestDto {
    private Long addressId;
}
