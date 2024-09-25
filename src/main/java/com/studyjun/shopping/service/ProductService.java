package com.studyjun.shopping.service;

import com.studyjun.shopping.dto.request.ProductRequest;
import com.studyjun.shopping.entity.Product;
import com.studyjun.shopping.repository.ProductRepository;
import com.studyjun.shopping.util.DefaultAssert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseEntity<?> createProduct(ProductRequest productRequest) {
        String imageUrl = null;
        if (productRequest.getImage() != null && !productRequest.getImage().isEmpty()) {
            imageUrl = saveImage(productRequest.getImage(), productRequest.getCategory());
        }

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .category(productRequest.getCategory())
                .imageUrl(imageUrl)
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
        if (productRequest.getImage() != null && !productRequest.getImage().isEmpty()) {
            product.setImageUrl(saveImage(productRequest.getImage(), productRequest.getCategory()));
        }

        productRepository.save(product);

        return ResponseEntity.ok("Product modify success");
    }

    public ResponseEntity<?> deleteProduct(String productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        DefaultAssert.isOptionalPresent(productOptional);

        Product product = productOptional.get();

        if (product.getImageUrl() != null) {
            deleteImage(product.getImageUrl());
        }

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

    private String saveImage(MultipartFile imageFile, String category) {
        try {
            LocalDate now = LocalDate.now();
            String year = String.valueOf(now.getYear());
            String month = String.format("%02d", now.getMonthValue());
            String day = String.format("%02d", now.getDayOfMonth());

            String uploadDir = "uploads/images/" + year + "/" + month + "/" + day + "/" + category + "/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return uploadDir + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }
    }

    private void deleteImage(String imageUrl) {
        try {
            Path filePath = Paths.get("uploads/images/" + imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image file", e);
        }
    }
}