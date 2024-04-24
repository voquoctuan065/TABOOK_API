package com.tacm.tabooksapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long id;
    private String title;
    private double price;
    private double discountedPrice;
    private int discountPercent;
    private String bookImage;
    private int quantity;
    private double total;
}
