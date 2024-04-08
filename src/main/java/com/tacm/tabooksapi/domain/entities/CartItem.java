package com.tacm.tabooksapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItem_id;

    @ManyToOne
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    private Books books;

    private Integer quantity;
    private Double price;
    private Double discounted_price;
    private Long user_id;

}
