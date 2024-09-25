package com.studyjun.shopping.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ProductRequest {
    @Schema(type = "string", example = "Product name test", description="상품에 대한 이름입니다.")
    @NotBlank
    @NotNull
    private String name;

    @Schema(type = "string", example = "Product description test", description="상품에 대한 설명입니다.")
    @NotBlank
    @NotNull
    private String description;

    @Schema(type = "string", example = "10000", description="상품에 대한 가격입니다.")
    @NotBlank
    @NotNull
    private int price;

    @Schema(type = "string", example = "10", description="상품에 대한 재고입니다.")
    @NotBlank
    @NotNull
    private int stockQuantity;

    @Schema(type = "string", example = "Product category test", description="상품에 대한 카테고리입니다.")
    @NotBlank
    @NotNull
    private String category;

    @Schema(type = "MultipartFile", example = "Product image file", description="상품에 대한 이미지 파일입니다.")
    @NotBlank
    @NotNull
    private MultipartFile image;
}