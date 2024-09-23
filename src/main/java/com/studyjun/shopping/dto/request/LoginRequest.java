package com.studyjun.shopping.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    @Schema(type = "string", example = "test@test.com", description="로그인 이메일입니다.")
    @NotBlank
    @NotNull
    private String email;

    @Schema(type = "string", example = "test", description="로그인 비밀번호입니다.")
    @NotBlank
    @NotNull
    private String password;
}