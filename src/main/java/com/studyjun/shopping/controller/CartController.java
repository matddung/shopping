package com.studyjun.shopping.controller;

import com.studyjun.shopping.service.CartService;
import com.studyjun.shopping.util.CurrentUser;
import com.studyjun.shopping.util.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart", description = "User interacts with cart")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @Operation(summary = "상품 추가", description = "장바구니(Cart)에 상품을 추가합니다.")
    @PostMapping("/add")
    private ResponseEntity<?> addProductInCart(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "productId 입력해주세요.", required = true) @RequestParam String productId) {
        return cartService.addProductInCart(userPrincipal, productId);
    }

    @Operation(summary = "상품 삭제", description = "장바구니(Cart)에 상품을 삭제합니다.")
    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteProductInCart(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "productId 입력해주세요.", required = true) @RequestParam String productId) {
        return cartService.deleteProductInCart(userPrincipal, productId);
    }

    @Operation(summary = "상품 목록", description = "장바구니(Cart)에 상품을 불러옵니다.")
    @GetMapping("/getList")
    private ResponseEntity<?> getProductListInCart(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal) {
        return cartService.getProductListInCart(userPrincipal);
    }
}