package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("Select c from Cart c where c.users.user_id = :user_id ")
    Cart findByUserId(@Param("user_id") Long user_id);
}
