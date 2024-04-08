package com.tacm.tabooksapi.domain.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentInfomation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payinfo_id;
    private String cardholder_name;
    private String card_number;
    private LocalDate expiration_date;
    private String cvv;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
