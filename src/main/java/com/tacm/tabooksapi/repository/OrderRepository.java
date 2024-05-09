package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o from Orders  o where o.users.userId = :userId AND (o.orderStatus = 'PLACED' OR o.orderStatus = 'CONFIRMED' OR o.orderStatus = 'DELIVERED')")
    public List<Orders> getUserOrders(@Param("userId") Long userId);

    List<Orders> findByUsers_UserId(Long user_id);

    List<Orders> findByOrderStatus(String pending);


    @Query("SELECT o FROM Orders o WHERE " +
            "o.orderStatus = 'PENDING' " +
            "AND ( " +
            "   :keyword IS NULL " +
            "   OR CONCAT(o.shippingAddress.fullName, ' ', " +
            "            o.shippingAddress.ward, ' ', " +
            "            o.shippingAddress.province, ' ', " +
            "            o.shippingAddress.district) " +
            "       LIKE %:keyword% " +
            ") " +
            "AND (:startTime IS NULL OR o.createdAt >= :startTime) " +
            "AND (:endTime IS NULL OR o.createdAt <= :endTime)")
    List<Orders> filterPendingOrder(@Param("keyword") String  keyword, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT o FROM Orders o WHERE " +
            "o.orderStatus = 'CONFIRMED' " +
            "AND ( " +
            "   :keyword IS NULL " +
            "   OR CONCAT(o.shippingAddress.fullName, ' ', " +
            "            o.shippingAddress.ward, ' ', " +
            "            o.shippingAddress.province, ' ', " +
            "            o.shippingAddress.district) " +
            "       LIKE %:keyword% " +
            ") " +
            "AND (:startTime IS NULL OR o.createdAt >= :startTime) " +
            "AND (:endTime IS NULL OR o.createdAt <= :endTime)")
    List<Orders> filterConfirmedOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT o FROM Orders o WHERE " +
            "o.orderStatus = 'SHIPPING' " +
            "AND ( " +
            "   :keyword IS NULL " +
            "   OR CONCAT(o.shippingAddress.fullName, ' ', " +
            "            o.shippingAddress.ward, ' ', " +
            "            o.shippingAddress.province, ' ', " +
            "            o.shippingAddress.district) " +
            "       LIKE %:keyword% " +
            ") " +
            "AND (:startTime IS NULL OR o.createdAt >= :startTime) " +
            "AND (:endTime IS NULL OR o.createdAt <= :endTime)")
    List<Orders> filterShippingOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT o FROM Orders o WHERE " +
            "o.orderStatus = 'DELIVERED' " +
            "AND ( " +
            "   :keyword IS NULL " +
            "   OR CONCAT(o.shippingAddress.fullName, ' ', " +
            "            o.shippingAddress.ward, ' ', " +
            "            o.shippingAddress.province, ' ', " +
            "            o.shippingAddress.district) " +
            "       LIKE %:keyword% " +
            ") " +
            "AND (:startTime IS NULL OR o.createdAt >= :startTime) " +
            "AND (:endTime IS NULL OR o.createdAt <= :endTime)")
    List<Orders> filterDeliveredOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime);
}
