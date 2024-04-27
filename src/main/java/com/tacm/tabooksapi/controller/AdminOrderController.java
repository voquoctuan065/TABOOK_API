package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.ApiResponse;
import com.tacm.tabooksapi.domain.entities.Orders;
import com.tacm.tabooksapi.exception.OrderException;
import com.tacm.tabooksapi.service.OrderService;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/order")
public class AdminOrderController {
    private OrderService orderService;

    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Orders>> getAllOrderHandler() {
        List<Orders> orders = orderService.getAllOrders();
        return new ResponseEntity<List<Orders>>(orders, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{order_id}/confirmed")
    public ResponseEntity<Orders> ConfirmedOrderHandler(@PathVariable Long order_id, @RequestHeader("Authorization") String token) throws OrderException {
        Orders orders = orderService.confirmedOrder(order_id);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @PutMapping("/{order_id}/ship")
    public ResponseEntity<Orders> ShippedOrderHandler(@PathVariable Long order_id, @RequestHeader("Authorization") String token) throws OrderException {
        Orders orders = orderService.shippedOrder(order_id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{order_id}/deliver")
    public ResponseEntity<Orders> DeliverOrderHandler(@PathVariable Long order_id, @RequestHeader("Authorization") String token) throws OrderException {
        Orders orders = orderService.deliveredOrder(order_id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

//    @PutMapping("/{order_id}/cancel")
//    public ResponseEntity<Orders> CancelOrderHandler(@PathVariable Long order_id, @RequestHeader("Authorization") String token) throws OrderException {
//        Orders orders = orderService.canceledOrder(order_id);
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }

    @DeleteMapping("/{order_id}/delete")
    public ResponseEntity<ApiResponse> DeleteOrderHandler(@PathVariable Long order_id, @RequestHeader("Authorization") String token) throws OrderException {
        orderService.deletedOrder(order_id);

        ApiResponse res = new ApiResponse();
        res.setMessage("Order được xoá thành công");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
