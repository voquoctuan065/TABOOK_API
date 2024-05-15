package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.OrderItem;
import com.tacm.tabooksapi.repository.BookRepository;
import com.tacm.tabooksapi.repository.OrderItemRepository;
import com.tacm.tabooksapi.service.BookRedisService;
import com.tacm.tabooksapi.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemRepository orderItemRepository;
    private BookRepository bookRepository;
    private BookRedisService bookRedisService;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository,
                                BookRepository bookRepository, BookRedisService bookRedisService) {
        this.orderItemRepository = orderItemRepository;
        this.bookRepository = bookRepository;
        this.bookRedisService = bookRedisService;
    }
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
