package com.tacm.tabooksapi.domain.dto;

import com.tacm.tabooksapi.domain.entities.Address;
import com.tacm.tabooksapi.domain.entities.OrderItem;
import com.tacm.tabooksapi.domain.entities.Orders;
import com.tacm.tabooksapi.domain.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long orderId;

    private UserDto userDto;

    private List<OrderItemDto> orderItemDto = new ArrayList<>();

    private Address shippingAddress;

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    private Double totalPrice;

    private Double discount;

    private String orderStatus;

    private Integer totalItem;
    private LocalDateTime createdAt;

    public static OrderDto fromEntity(Orders orders) {
        return new OrderDto(
                orders.getOrderId(),
                UserDto.fromEntity(orders.getUsers()),
                orders.getOrderItem().stream().map(OrderItemDto::fromEntity).collect(Collectors.toList()),
                orders.getShippingAddress(),
                orders.getOrderDate(),
                orders.getDeliveryDate(),
                orders.getTotalPrice(),
                orders.getDiscount(),
                orders.getOrderStatus(),
                orders.getTotalItem(),
                orders.getCreatedAt()
        );
    }
}
