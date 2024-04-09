package com.tacm.tabooksapi.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetail {
    private String paymentMethod;
    private String paymentStatus;
    private String paymentId;
    private String paymentLinkId;
    private String paymentLinkReference;
    private String paymentLinkStatus;
    private String thirdPaymentId;
}
