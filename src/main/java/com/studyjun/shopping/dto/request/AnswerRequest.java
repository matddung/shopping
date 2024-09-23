package com.studyjun.shopping.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerRequest {
    @Schema(type = "string", example = "Answer test", description="질문에 대한 답변입니다.")
    @NotBlank
    @NotNull
    private String content;
}