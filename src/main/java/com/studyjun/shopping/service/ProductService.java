package com.studyjun.shopping.service;

import com.studyjun.shopping.dto.request.ProductRequest;
import com.studyjun.shopping.entity.Product;
import com.studyjun.shopping.repository.ProductRepository;
import com.studyjun.shopping.util.DefaultAssert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseEntity<?> createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .category(productRequest.getCategory())
                .imageUrl(productRequest.getImageUrl())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        productRepository.save(product);

        return ResponseEntity.ok("Product create success");
    }

    public ResponseEntity<?> modifyProduct(String productId, ProductRequest productRequest) {
        Optional<Product> productOptional = productRepository.findById(productId);
        DefaultAssert.isOptionalPresent(productOptional);

        Product product = productOptional.get();

        if (productRequest.getName() != null) {
            product.setName(productRequest.getName());
        }
        if (productRequest.getDescription() != null) {
            product.setDescription(productRequest.getDescription());
        }
        if (productRequest.getPrice() != 0) {
            product.setPrice(productRequest.getPrice());
        }
        if (productRequest.getStockQuantity() != 0) {
            product.setStockQuantity(productRequest.getStockQuantity());
        }
        if (productRequest.getCategory() != null) {
            product.setCategory(productRequest.getCategory());
        }
        if (productRequest.getImageUrl() != null) {
            product.setImageUrl(productRequest.getImageUrl());
        }

        productRepository.save(product);

        return ResponseEntity.ok("Product modify success");
    }

    public ResponseEntity<?> deleteProduct(String productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        DefaultAssert.isOptionalPresent(productOptional);

        productRepository.delete(productOptional.get());

        return ResponseEntity.ok("Product delete success");
    }

    public ResponseEntity<?> getProductDetail(String productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        DefaultAssert.isOptionalPresent(productOptional);

        return ResponseEntity.ok(productOptional.get());
    }

    // stockQuantity개 이하인 제품 목록 보기
    public ResponseEntity<?> findByStockQuantityLessThanEqual(int stockQuantity) {
        List<Product> lowStockProducts = productRepository.findByStockQuantityLessThanEqual(stockQuantity);

        return ResponseEntity.ok(lowStockProducts);
    }
}