package com.studyjun.shopping.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewRequest {
    @Schema(type = "string", example = "productId1", description="productId(PK) 입니다.")
    @NotBlank
    @NotNull
    private String productId;

    @Schema(type = "int", example = "5", description="평가 점수입니다.")
    @NotBlank
    @NotNull
    private int rating;

    @Schema(type = "string", example = "만족스러워요!", description="상품평입니다.")
    @NotBlank
    @NotNull
    private String reviewText;
}