package com.studyjun.shopping.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoRequest {
    @Schema(type = "string", example = "010-0101-0101", description="휴대폰 번호입니다.")
    @NotBlank
    @NotNull
    private String phoneNumber;

    @Schema(type = "string", example = "서울특별시 강남구", description="주소입니다.")
    @NotBlank
    @NotNull
    private String address;

    @Schema(type = "string", example = "960604", description="생년월일입니다.")
    @NotBlank
    @NotNull
    private String birth;
}