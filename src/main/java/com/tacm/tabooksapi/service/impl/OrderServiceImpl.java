package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.dto.CartItem;
import com.tacm.tabooksapi.domain.dto.CartItemsRq;
import com.tacm.tabooksapi.domain.entities.*;
import com.tacm.tabooksapi.exception.OrderException;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.repository.*;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private BookService bookService;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl( BookService bookService,  AddressRepository addressRepository
    ,UserRepository userRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository
    ) {
        this.bookService = bookService;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Orders createOrder(Users users, CartItemsRq cartItemsRq) throws ProductException {
        // Check if the shipping address already exists in the database
        Address address = addressRepository.findByFullNameAndStreetAddressAndWardAndDistrictAndProvinceAndZipCodeAndPhoneNumber(
                cartItemsRq.getShippingAddress().getFullName(),
                cartItemsRq.getShippingAddress().getStreetAddress(),
                cartItemsRq.getShippingAddress().getWard(),
                cartItemsRq.getShippingAddress().getDistrict(),
                cartItemsRq.getShippingAddress().getProvince(),
                cartItemsRq.getShippingAddress().getZipCode(),
                cartItemsRq.getShippingAddress().getPhoneNumber()
        );

        // If not found, save the new shipping address to the database
        if (address == null) {
            address = addressRepository.save(cartItemsRq.getShippingAddress());
            address.setUsers(users);
            users.getAddress().add(address);
            userRepository.save(users);
        }

        List<CartItem> cartItems = cartItemsRq.getCartItems();

        List<OrderItem> orderItems = null;
        if (cartItems != null) {
            orderItems = new ArrayList<>();
            for (CartItem item : cartItems) {
                OrderItem orderItem = new OrderItem();
                Books books = bookService.findBookById(item.getId());

                orderItem.setPrice(item.getPrice());
                orderItem.setBooks(books);
                orderItem.setQuantity(item.getQuantity());
                orderItem.setDiscountedPrice(item.getDiscountedPrice());
                orderItem.setUserId(users.getUserId());

                OrderItem createdOrderItem = orderItemRepository.save(orderItem);
                orderItems.add(createdOrderItem);
            }
        }

        Orders createdOrder = new Orders();
        createdOrder.setUsers(users);
        createdOrder.setOrderItem(orderItems);
        createdOrder.setTotalPrice(cartItemsRq.getCartTotalAmount());
        createdOrder.setTotalItem(cartItemsRq.getCartTotalQuantity());
        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setCreatedAt(LocalDateTime.now());
        Orders savedOrder = orderRepository.save(createdOrder);

        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder); // Update OrderItem's order field
            orderItemRepository.save(item);
        }
        return savedOrder;
    }

    @Override
    public Orders findOderById(Long id) throws OrderException {
        Optional<Orders> orders = orderRepository.findById(id);
        return orders.get();
    }

    @Override
    public List<Orders> userOrderHistory(Long userId) {
        List<Orders> orders = orderRepository.findByUsers_UserId(userId);
         return orders;
    }

    @Override
    public Orders placedOrder(Long id) throws OrderException {
        Orders orders = findOderById(id);
        orders.setOrderStatus("PLACED");
        return orders;
    }

    @Override
    public Orders confirmedOrder(Long id) throws OrderException {
        Orders orders = findOderById(id);
        orders.setOrderStatus("CONFIRMED");
        return orderRepository.save(orders);
    }

    @Override
    public Orders shippedOrder(Long id) throws OrderException {
        Orders orders = findOderById(id);
        orders.setOrderStatus("SHIPPED");
        return orderRepository.save(orders);
    }

    @Override
    public Orders deliveredOrder(Long id) throws OrderException {
        Orders orders = findOderById(id);
        orders.setOrderStatus("DELIVERED");
        return orderRepository.save(orders);

    }

    @Override
    public void canceledOrder(Long id) throws OrderException {
        Orders orders = findOderById(id);
        orders.setOrderStatus("CANCELLED");
        orderRepository.save(orders);
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deletedOrder(Long orderId) throws OrderException {
        orderRepository.deleteById(orderId);
    }
}
