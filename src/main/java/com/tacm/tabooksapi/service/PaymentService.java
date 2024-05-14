package com.tacm.tabooksapi.service;

import com.stripe.exception.StripeException;
import com.tacm.tabooksapi.domain.dto.PaymentInfoDto;
import com.tacm.tabooksapi.domain.dto.PaymentResDto;
import com.tacm.tabooksapi.domain.entities.Orders;
import com.tacm.tabooksapi.domain.entities.PaymentInfo;
import com.tacm.tabooksapi.exception.OrderException;

public interface PaymentService {
    public PaymentResDto createPaymentLink(Long totalAmount, Long orderId) throws StripeException;

    void successStripePayment(Long userId, Long orderId, Long totalAmount) throws OrderException;

    void successCODPayment(Long userId, Long orderId, Long totalAmount) throws OrderException;

    PaymentInfoDto getPaymentInfoByOrderId(Long orderId);
}
