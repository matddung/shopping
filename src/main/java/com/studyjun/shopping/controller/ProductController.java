package com.studyjun.shopping.controller;

import com.studyjun.shopping.dto.request.ProductRequest;
import com.studyjun.shopping.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product", description = "Administrator interacts with product")
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 생성", description = "상품을 작성합니다.")
    @PostMapping("/create")
    private ResponseEntity<?> createProduct(
            @Parameter(description = "productRequest를 작성해주세요.", required = true) @RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @Operation(summary = "상품 수정", description = "상품을 수정합니다.")
    @PatchMapping("/modify")
    private ResponseEntity<?> modifyProduct(
            @Parameter(description = "productId를 입력해주세요.", required = true) @RequestParam String productId,
            @Parameter(description = "productRequest를 작성해주세요.", required = true) @RequestBody ProductRequest productRequest) {
        return productService.modifyProduct(productId, productRequest);
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteProduct(
            @Parameter(description = "productId를 입력해주세요.", required = true) @RequestParam String productId) {
        return productService.deleteProduct(productId);
    }

    @Operation(summary = "상품 보기", description = "상품의 상세 정보를 보여줍니다.")
    @GetMapping("/detail")
    private ResponseEntity<?> getProductDetail(
            @Parameter(description = "productId를 입력해주세요.", required = true) @RequestParam String productId) {
        return productService.getProductDetail(productId);
    }

    @Operation(summary = "재고 확인", description = "입력한 숫자보다 재고가 적은 상품을 확인합니다.")
    @GetMapping("/lowStock")
    private ResponseEntity<?> findByStockQuantityLessThanEqual(
            @Parameter(description = "stockQuantity를 입력해주세요.", required = true) @RequestParam int stockQuantity) {
        return productService.findByStockQuantityLessThanEqual(stockQuantity);
    }
}