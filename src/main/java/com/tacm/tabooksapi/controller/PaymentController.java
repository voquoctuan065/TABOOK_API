package com.tacm.tabooksapi.controller;

import com.stripe.exception.StripeException;
import com.tacm.tabooksapi.domain.dto.PaymentInfoDto;
import com.tacm.tabooksapi.domain.dto.PaymentResDto;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.OrderException;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.service.PaymentService;
import com.tacm.tabooksapi.service.UserService;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.stripe.model.PaymentIntent;

@RestController
@RequestMapping("/public/payment")
public class PaymentController {

    private PaymentService paymentService;
    private UserService userService;

    @Autowired
    public PaymentController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentResDto> createPayment(@RequestParam("totalAmount") Long totalAmount,
                                                       @RequestParam("orderId") Long orderId,
                                                       @RequestHeader("Authorization") String jwt) throws StripeException {
        PaymentResDto res = paymentService.createPaymentLink(totalAmount, orderId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/success")
    public ResponseEntity<String> saveStripePaymentInfoDto(@RequestParam("orderId") Long orderId,
                                                     @RequestParam("totalAmount") Long totalAmount,
                                                     @RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        Users users = userService.findUserProfileByJwt(jwt);
        paymentService.successStripePayment(users.getUserId(), orderId, totalAmount);

        return new ResponseEntity<>("Payment infor saved successfully", HttpStatus.OK);
    }

    @PostMapping("/cod")
    public ResponseEntity<String> saveCODPaymentInfoDto(@RequestParam("orderId") Long orderId,
                                                     @RequestParam("totalAmount") Long totalAmount,
                                                     @RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        Users users = userService.findUserProfileByJwt(jwt);
        paymentService.successCODPayment(users.getUserId(), orderId, totalAmount);
        return new ResponseEntity<>("Payment infor saved successfully", HttpStatus.OK);
    }
}
