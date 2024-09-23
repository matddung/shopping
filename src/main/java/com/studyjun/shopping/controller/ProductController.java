package com.studyjun.shopping.controller;

import com.studyjun.shopping.dto.request.ProductRequest;
import com.studyjun.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    private ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PatchMapping("/modify")
    private ResponseEntity<?> modifyProduct(@RequestParam String productId, @RequestBody ProductRequest productRequest) {
        return productService.modifyProduct(productId, productRequest);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteProduct(@RequestParam String productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/detail")
    private ResponseEntity<?> getProductDetail(@RequestParam String productId) {
        return productService.getProductDetail(productId);
    }

    @GetMapping("/lowStock")
    private ResponseEntity<?> findByStockQuantityLessThanEqual(@RequestParam int stockQuantity) {
        return productService.findByStockQuantityLessThanEqual(stockQuantity);
    }
}