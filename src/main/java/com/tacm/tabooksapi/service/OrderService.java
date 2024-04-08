package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.entities.Address;
import com.tacm.tabooksapi.domain.entities.Orders;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.OrderException;

import java.util.List;

public interface OrderService {
    public Orders createOrder(Users users, Address shippingAddress);

    public Orders findOderById(Long id) throws OrderException;

    public List<Orders> userOrderHistory(Long userId);

    public Orders placedOrder(Long id) throws  OrderException;

    public Orders confirmedOrder(Long id) throws  OrderException;

    public Orders shippedOrder(Long id) throws  OrderException;
    public Orders deliveredOrder(Long id) throws  OrderException;
    public Orders canceledOrder(Long id) throws  OrderException;

    List<Orders> getAllOrders();

    void deletedOrder(Long orderId);
}
