package com.tacm.tabooksapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_info_id")
    private Long paymentInfoId;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Orders order;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "payment_status")
    private String paymentStatus;
}
