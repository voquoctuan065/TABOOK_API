package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.Address;
import com.tacm.tabooksapi.domain.entities.Orders;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.OrderException;
import com.tacm.tabooksapi.repository.CartRepository;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.CartService;
import com.tacm.tabooksapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private CartRepository cartRepository;
    private CartService cartItemService;

    private BookService bookService;

    @Autowired
    public OrderServiceImpl(CartRepository cartRepository, CartService cartItemService, BookService bookService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.bookService = bookService;
    }

    @Override
    public Orders createOrder(Users users, Address shippingAddress) {
        return null;
    }

    @Override
    public Orders findOderById(Long id) throws OrderException {
        return null;
    }

    @Override
    public List<Orders> userOrderHistory(Long userId) {
        return null;
    }

    @Override
    public Orders placedOrder(Long id) throws OrderException {
        return null;
    }

    @Override
    public Orders confirmedOrder(Long id) throws OrderException {
        return null;
    }

    @Override
    public Orders shippedOrder(Long id) throws OrderException {
        return null;
    }

    @Override
    public Orders deliveredOrder(Long id) throws OrderException {
        return null;
    }

    @Override
    public Orders canceledOrder(Long id) throws OrderException {
        return null;
    }

    @Override
    public List<Orders> getAllOrders() {
        return null;
    }

    @Override
    public void deletedOrder(Long orderId) {

    }
}
