package com.studyjun.shopping.controller;

import com.studyjun.shopping.service.CartService;
import com.studyjun.shopping.util.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    private ResponseEntity<?> addProductInCart(UserPrincipal userPrincipal, String productId) {
        return cartService.addProductInCart(userPrincipal, productId);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteProductInCart(UserPrincipal userPrincipal, String productId) {
        return cartService.deleteProductInCart(userPrincipal, productId);
    }
}
