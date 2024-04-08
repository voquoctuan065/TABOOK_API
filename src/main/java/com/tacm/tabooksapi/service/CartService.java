package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.dto.AddItemRequest;
import com.tacm.tabooksapi.domain.entities.Cart;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ProductException;

public interface CartService {
    Cart createCart(Users users);

    String addCartItem(Long user_id, AddItemRequest rq) throws ProductException;

    Cart findUserCart(Long user_id);

}
