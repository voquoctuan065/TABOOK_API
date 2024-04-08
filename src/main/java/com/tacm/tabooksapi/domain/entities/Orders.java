package com.tacm.tabooksapi.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    @ManyToOne
    private Users users;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> order_item = new ArrayList<>();

    private LocalDateTime order_date;
    private LocalDateTime delivery_date;

    @OneToOne
    private Address shipping_address;

    @Embedded
    private PaymentDetail payment_detail = new PaymentDetail();

    private Double total_price;
    private Double totalDiscountedPrice;
    private Double discount;

    private String order_status;

    private Integer total_item;

    private LocalDateTime created_at;
}
