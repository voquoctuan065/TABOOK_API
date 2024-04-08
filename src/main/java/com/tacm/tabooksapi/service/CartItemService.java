package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Cart;
import com.tacm.tabooksapi.domain.entities.CartItem;
import com.tacm.tabooksapi.exception.CartItemException;
import com.tacm.tabooksapi.exception.UserException;

public interface CartItemService {
    CartItem createCartItem(CartItem cartItem);

    CartItem updateCartItem(Long user_id, Long id, CartItem cartItem) throws CartItemException, UserException;
    CartItem isCartItemExist(Cart cart, Books books, Long user_id);
    void removeCartItem(Long user_id, Long cartItem_id) throws CartItemException, UserException;
    CartItem findCartItemById(Long cartItem_id) throws CartItemException;

}
