package com.tacm.tabooksapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoDto {

    private Long paymentInfoId;

    private Long userId;

    private Long orderId;

    private Long total_Amount;

    private String paymentStatus;
}
