package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.ApiResponse;
import com.tacm.tabooksapi.domain.dto.CartItemsRq;
import com.tacm.tabooksapi.domain.entities.Orders;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ApiException;
import com.tacm.tabooksapi.exception.OrderException;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.service.OrderService;
import com.tacm.tabooksapi.service.PaymentService;
import com.tacm.tabooksapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/public/order")
public class OrderController {
    private OrderService orderService;
    private UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(
                                              @RequestBody CartItemsRq cartItemsRq,
                                              @RequestHeader("Authorization") String token) throws UserException, ProductException {
        Users users = userService.findUserProfileByJwt(token);

        Orders orders = orderService.createOrder(users, cartItemsRq);

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

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable("orderId") Long orderId) throws ApiException {
        try {
            orderService.deletedOrder(orderId);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Xoá order thành công!");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long orderId,
                                                   @RequestHeader("Authorization") String jwt
                                                   ) throws ApiException {
        try {
            orderService.canceledOrder(orderId);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Huỷ đơn hàng thành công!");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
