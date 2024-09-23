package com.studyjun.shopping.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "answer")
public class Answer {
    @Id
    private String questionId;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public Answer(String questionId, String content) {
        this.questionId = questionId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}