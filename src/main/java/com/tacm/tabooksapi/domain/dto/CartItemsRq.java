package com.tacm.tabooksapi.domain.dto;

import com.tacm.tabooksapi.domain.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsRq {
    private List<CartItem> cartItems;
    private int cartTotalQuantity;
    private double cartTotalAmount;
    private Address shippingAddress;
}
