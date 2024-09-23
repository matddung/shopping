package com.studyjun.shopping.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionRequest {
    @Schema(type = "string", example = "Question subejct test", description="질문의 제목입니다.")
    @NotBlank
    @NotNull
    private String subject;

    @Schema(type = "string", example = "Question content test", description="질문의 내용입니다.")
    @NotBlank
    @NotNull
    private String content;
}