package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.AddItemRequest;
import com.tacm.tabooksapi.domain.dto.ApiResponse;
import com.tacm.tabooksapi.domain.entities.Cart;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.service.CartService;
import com.tacm.tabooksapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cart")
public class CartController {
    private CartService cartService;
    private UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String token) throws UserException {
        Users users = userService.findUserProfileByJwt(token);
        Cart cart = cartService.findUserCart(users.getUserId());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
                                                     @RequestHeader("Authorization") String token) throws UserException, ProductException {
        Users users = userService.findUserProfileByJwt(token);
        cartService.addCartItem(users.getUserId(), req);
        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
