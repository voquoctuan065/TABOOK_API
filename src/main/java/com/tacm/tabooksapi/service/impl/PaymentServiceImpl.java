package com.tacm.tabooksapi.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.tacm.tabooksapi.domain.dto.PaymentResDto;
import com.tacm.tabooksapi.domain.entities.Orders;
import com.tacm.tabooksapi.domain.entities.PaymentInfo;
import com.tacm.tabooksapi.exception.OrderException;
import com.tacm.tabooksapi.repository.OrderRepository;
import com.tacm.tabooksapi.repository.PaymentRepository;
import com.tacm.tabooksapi.service.OrderService;
import com.tacm.tabooksapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService  {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    private PaymentRepository paymentRepository;
    private OrderService orderService;

    private OrderRepository orderRepository;
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderService orderService,
                              OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.orderRepository= orderRepository;
    }
    @Override
    public PaymentResDto createPaymentLink(Long totalAmount, Long orderId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment/success?order_id="+orderId+"&total_amount="+totalAmount)
                .setCancelUrl("http://localhost:3000/payment/fail?order_id="+orderId+"&total_amount="+totalAmount)
                .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L).
                        setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("VND")
                                .setUnitAmount(totalAmount)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("Số tiền thanh toán").build()).build()
                        ).build()
                ).build()
                ;
        Session session = Session.create(params);
        PaymentResDto paymentResDto = new PaymentResDto();
        paymentResDto.setPaymentUrl(session.getUrl());
        return paymentResDto;
    }

    @Override
    public void successStripePayment(Long userId, Long orderId, Long totalAmount) throws OrderException {
        Orders orders = orderService.findOderById(orderId);

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrder(orders);
        paymentInfo.setTotalAmount(totalAmount);
        paymentInfo.setPaymentStatus("Đã thanh toán");
        paymentRepository.save(paymentInfo);

        if(orders != null) {
            orders.setPaymentInfo(paymentInfo);
            orderRepository.save(orders);
        }
    }

    @Override
    public void successCODPayment(Long userId, Long orderId, Long totalAmount) throws OrderException {
        Orders orders = orderService.findOderById(orderId);

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrder(orders);
        paymentInfo.setTotalAmount(totalAmount);
        paymentInfo.setPaymentStatus("Chờ thanh toán");
        paymentRepository.save(paymentInfo);

        if(orders != null) {
            orders.setPaymentInfo(paymentInfo);
            orderRepository.save(orders);
        }
    }


}
