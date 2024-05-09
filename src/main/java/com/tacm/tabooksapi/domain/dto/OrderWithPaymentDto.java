package com.tacm.tabooksapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWithPaymentDto {
    private OrderDto orderDto;
    private PaymentInfoDto paymentInfoDto;
}
