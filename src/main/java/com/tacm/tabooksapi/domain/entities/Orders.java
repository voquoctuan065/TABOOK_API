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
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    private Users users;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItem = new ArrayList<>();

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @OneToOne
    private Address shippingAddress;

    @Embedded
    private PaymentDetail paymentDetail = new PaymentDetail();

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_discounted_price")
    private Double totalDiscountedPrice;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "total_item")
    private Integer totalItem;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
