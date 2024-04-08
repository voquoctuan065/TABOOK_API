package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Cart;
import com.tacm.tabooksapi.domain.entities.CartItem;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.CartItemException;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.repository.CartItemRepository;
import com.tacm.tabooksapi.repository.CartRepository;
import com.tacm.tabooksapi.service.CartItemService;
import com.tacm.tabooksapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;
    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository
    , UserService userService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }
    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getBooks().getBook_price() * cartItem.getQuantity());
        cartItem.setDiscounted_price(cartItem.getBooks().getDiscounted_price() * cartItem.getQuantity());
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItem(Long user_id, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        Users user = userService.findUserById(item.getUser_id());
        if(user.getUser_id().equals(user_id)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getBooks().getBook_price());
            item.setDiscounted_price(item.getBooks().getDiscounted_price()*item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Books books, Long user_id) {
        CartItem cartItem  = cartItemRepository.isCartItemExist(cart, books, user_id);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long user_id, Long cartItem_id) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItem_id);

        Users user = userService.findUserById(cartItem.getUser_id());

        Users reqUser = userService.findUserById(user_id);

        if(user.getUser_id().equals(reqUser.getUser_id())) {
            cartItemRepository.deleteById(cartItem_id);
        }
        else {
            throw new UserException("Bạn không thể xoá item của người dùng khác");
        }


    }

    @Override
    public CartItem findCartItemById(Long cartItem_id) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItem_id);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw new CartItemException("Không tìm thấy cartItem với id + " + cartItem_id);
    }
}
