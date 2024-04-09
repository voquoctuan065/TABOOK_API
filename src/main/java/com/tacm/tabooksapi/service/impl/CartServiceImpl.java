package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.dto.AddItemRequest;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Cart;
import com.tacm.tabooksapi.domain.entities.CartItem;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.repository.CartRepository;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.CartItemService;
import com.tacm.tabooksapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private BookService bookService;
    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, BookService bookService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.bookService = bookService;
    }


    @Override
    public Cart createCart(Users users) {

        Cart cart = new Cart();

        cart.setUsers(users);

        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long user_id, AddItemRequest rq) throws ProductException {
        Cart cart = cartRepository.findByUserId(user_id);

        Books books = bookService.findBookById(rq.getBookId());
        CartItem isPresent = cartItemService.isCartItemExist(cart, books, user_id);
        if (isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setBooks(books);
            cartItem.setCart(cart);
            cartItem.setQuantity(rq.getQuantity());
            cartItem.setUserId(user_id);

            double price = rq.getQuantity()*books.getDiscountedPrice();
            cartItem.setPrice(price);

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItem().add(createdCartItem);
        }
        return "Đã thêm sản phẩm vào giỏ hàng";
    }

    @Override
    public Cart findUserCart(Long user_id) {
        Cart cart = cartRepository.findByUserId(user_id);
        double total_price = 0;
        double totalDiscounted_price = 0;
        int total_item = 0;

        for(CartItem cartItem : cart.getCartItem()) {
            total_price = total_price + cartItem.getPrice();
            totalDiscounted_price = totalDiscounted_price + cartItem.getDiscountedPrice();
            total_item = total_item + cartItem.getQuantity();
        }

        cart.setTotalPrice(total_price);
        cart.setTotalDiscounted(totalDiscounted_price);
        cart.setTotalItem(total_item);
        cart.setDiscounted(total_price - totalDiscounted_price);

        return cartRepository.save(cart);
    }
}
