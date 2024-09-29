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

    @Operation(summary = "상품 리스트", description = "선택한 카테고리의 상품 리스트를 보여줍니다.")
    @GetMapping("/list")
    private ResponseEntity<?> findByCategory(
            @Parameter(description = "category를 입력해주세요.", required = true) @RequestParam String category) {
        return productService.getProductList(category);
    }

    @Operation(summary = "검색", description = "상품을 검색하고, 지정한 방식대로 정렬합니다.")
    @GetMapping("/search")
    private ResponseEntity<?> searchProduct(
            @Parameter(description = "name를 입력해주세요.", required = true) @RequestParam String name,
            @Parameter(description = "sortField를 입력해주세요.", required = true) @RequestParam(defaultValue = "name") String sortField,
            @Parameter(description = "sortOrder를 입력해주세요.", required = true) @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        return productService.searchProduct(name, sortField, sortOrder);
    }

    @Operation(summary = "카테고리 내 검색",description = "카테고리 내에서 상품 명을 검색합니다.")
    @GetMapping("/categorySearch")
    private ResponseEntity<?> searchProductInCategory(
            @Parameter(description = "name를 입력해주세요.", required = true) @RequestParam String name,
            @Parameter(description = "category를 입력해주세요.", required = true) @RequestParam String cateogry,
            @Parameter(description = "sortField를 입력해주세요.", required = true) @RequestParam(defaultValue = "name") String sortField,
            @Parameter(description = "sortOrder를 입력해주세요.", required = true) @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        return productService.searchProductInCategory(name, cateogry, sortField, sortOrder);
    }
}