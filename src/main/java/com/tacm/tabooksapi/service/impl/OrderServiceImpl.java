package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.dto.AddressDto;
import com.tacm.tabooksapi.domain.dto.CartItem;
import com.tacm.tabooksapi.domain.dto.CartItemsRq;
import com.tacm.tabooksapi.domain.entities.*;
import com.tacm.tabooksapi.exception.OrderException;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.repository.*;
import com.tacm.tabooksapi.service.BookRedisService;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.OrderService;
import com.tacm.tabooksapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private BookService bookService;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;
    private BookRedisService bookRedisService;
    @Autowired
    public OrderServiceImpl( BookService bookService,
                             AddressRepository addressRepository,
                             UserRepository userRepository,
                             OrderItemRepository orderItemRepository,
                             OrderRepository orderRepository,
                             BookRedisService bookRedisService
    ) {
        this.bookService = bookService;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.bookRedisService = bookRedisService;
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
            address = addressRepository.save(AddressDto.fromDto(cartItemsRq.getShippingAddress()));
            address.setUsers(users);
            users.getAddress().add(address);
            userRepository.save(users);
        }

        List<CartItem> cartItems = cartItemsRq.getCartItem();

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

                int newStockQuantity = books.getStockQuantity() - item.getQuantity();
                if (newStockQuantity >= 0) {
                    books.setStockQuantity(newStockQuantity);
                    bookService.updateBook(books, books.getBookId());
                    bookRedisService.clear();
                } else {
                    throw new IllegalArgumentException("Not enough stock available.");
                }

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
        orders.setOrderStatus("SHIPPING");
        return orderRepository.save(orders);
    }

    @Override
    public Orders deliveredOrder(Long id) throws OrderException {
        Orders orders = findOderById(id);
        orders.setOrderStatus("DELIVERED");
        if (orders.getPaymentInfo() != null) {
            PaymentInfo paymentInfo = orders.getPaymentInfo();
            paymentInfo.setPaymentStatus("Đã thanh toán");
        }

        return orderRepository.save(orders);
    }

    @Override
    public void canceledOrder(Long id) throws OrderException {
        Orders orders = findOderById(id);
        orders.setOrderStatus("CANCELLED");
        orderRepository.save(orders);
    }

    @Override
    public Orders packedOrder(Long id) throws OrderException {
        Orders orders = findOderById(id);
        orders.setOrderStatus("PACKED");
        return orderRepository.save(orders);
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deletedOrder(Long orderId) throws OrderException {
        orderRepository.deleteById(orderId);
    }

//    @Override
//    public List<Orders> filterPendingOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime) {
//        return orderRepository.filterPendingOrder(keyword, startTime, endTime);
//    }

    @Override
    public Page<Orders> filterPendingOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return orderRepository.filterPendingOrder(keyword, startTime, endTime, pageable);
    }

    @Override
    public Page<Orders> filterConfirmedOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return orderRepository.filterConfirmedOrder(keyword, startTime, endTime, pageable);
    }

    @Override
    public Page<Orders> filterShippingOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return orderRepository.filterShippingOrder(keyword, startTime , endTime, pageable);
    }

    @Override
    public Page<Orders> filterDeliveredOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable ) {
        return orderRepository.filterDeliveredOrder(keyword, startTime, endTime, pageable);
    }

    @Override
    public Page<Orders> filterPackedOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return orderRepository.filterPackedOrder(keyword, startTime, endTime, pageable);
    }
}
