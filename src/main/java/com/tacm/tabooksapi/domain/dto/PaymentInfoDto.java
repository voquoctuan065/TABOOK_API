package com.tacm.tabooksapi.domain.dto;

import com.tacm.tabooksapi.domain.entities.PaymentInfo;
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

    public static PaymentInfoDto fromEntity(PaymentInfo paymentInfo) {
        return new PaymentInfoDto(
                paymentInfo.getPaymentInfoId(),
                null,
                paymentInfo.getOrder().getOrderId(),
                paymentInfo.getTotalAmount(),
                paymentInfo.getPaymentStatus()
        );
    }
}
