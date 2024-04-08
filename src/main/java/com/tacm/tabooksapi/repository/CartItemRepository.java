package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Cart;
import com.tacm.tabooksapi.domain.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository  extends JpaRepository<CartItem, Long> {
    @Query("select ci from CartItem ci where ci.cart = :cart and ci.books = :books and ci.user_id = :user_id")
    CartItem isCartItemExist(
            @Param("cart")Cart cart, @Param("books")Books books, @Param("user_id") Long user_id
            );
}
