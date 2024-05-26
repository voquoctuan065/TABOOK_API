package com.tacm.tabooksapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.OrderItem;
import com.tacm.tabooksapi.domain.entities.Orders;
import com.tacm.tabooksapi.mapper.impl.BookMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.print.Book;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Long orderItemId;

    private BookOrderDto bookOrderDto;

    private Integer quantity;

    private Double price;

    private Double discountedPrice;

    private Long userId;


    public static OrderItemDto fromEntity(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getOrderItemId(),
                BookOrderDto.fromEntity(orderItem.getBooks()),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getDiscountedPrice(),
                orderItem.getUserId()
        );
    }
}
