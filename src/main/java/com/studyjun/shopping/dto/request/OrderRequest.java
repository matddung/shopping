package com.studyjun.shopping.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequest {
    @Schema(type = "List<String>", example = "{\"A\", \"B\", \"C\"}", description="상품 아이디 리스트입니다.")
    @NotBlank
    @NotNull
    private List<String> productIdsInCart;

    @Schema(type = "string", example = "서울특별시 강남구", description="배송받을 주소입니다.")
    @NotBlank
    @NotNull
    private String address;
}