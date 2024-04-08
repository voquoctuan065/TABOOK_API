package com.tacm.tabooksapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderitem_id;

    @ManyToOne
    @JsonIgnore
    private Orders order;

    @ManyToOne
    private Books books;

    private Integer quantity;
    private Double price;
    private Double discounted_price;
    private Long user_id;
    private LocalDateTime delivery_date;
}
