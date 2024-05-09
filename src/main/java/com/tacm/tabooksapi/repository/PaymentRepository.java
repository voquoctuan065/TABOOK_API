package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentInfo, Long> {
    PaymentInfo findByOrderOrderId(Long orderId);
}
