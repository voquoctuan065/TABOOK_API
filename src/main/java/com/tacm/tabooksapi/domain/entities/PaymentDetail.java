package com.tacm.tabooksapi.domain.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetail {
    private String payment_method;
    private String payment_status;
    private String payment_id;
    private String paymentLink_id;
    private String paymentLink_Reference;
    private String paymentLink_status;
    private String thirdPayment_id;
}
