package com.studyjun.shopping.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentRequest {
    @Schema(type = "string", example = "IMP_UID", description="결제 고유 ID 입니다.")
    @NotBlank
    @NotNull
    private String impUid;

    @Schema(type = "string", example = "CARD", description="결제 방법입니다.")
    @NotBlank
    @NotNull
    private String paymentMethod;

    @Schema(type = "string", example = "order1", description="orderId(PK)입니다.")
    @NotBlank
    @NotNull
    private String orderId;
}