package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.entities.Address;
import com.tacm.tabooksapi.domain.entities.Orders;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.OrderException;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.service.OrderService;
import com.tacm.tabooksapi.service.UserService;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/order")
public class OrderController {
    private OrderService orderService;
    private UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<Orders> createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization") String token) throws UserException {
        Users users = userService.findUserProfileByJwt(token);

        Orders orders = orderService.createOrder(users, shippingAddress);

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Orders>> userOrderHistory(@RequestHeader("Authorization") String token) throws UserException  {
        Users users = userService.findUserProfileByJwt(token);

        List<Orders> orders = orderService.userOrderHistory(users.getUserId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> findOrderById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) throws UserException, OrderException {
        Users users = userService.findUserProfileByJwt(token);
        Orders orders = orderService.findOderById(id);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}
