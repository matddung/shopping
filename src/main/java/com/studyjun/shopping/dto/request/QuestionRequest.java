package com.studyjun.shopping.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionRequest {
    private String subject;
    private String content;
}