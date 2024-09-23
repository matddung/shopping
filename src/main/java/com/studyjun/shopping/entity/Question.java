package com.studyjun.shopping.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "question")
public class Question {
    @Id
    private String id;
    private String subject;
    private String content;
    @CreatedBy
    private String userId;
    private String boardCategory;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
    private Answer answer;

    @Builder
    public Question(String subject, String content, String userId, String boardCategory, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.subject = subject;
        this.content = content;
        this.userId = userId;
        this.boardCategory = boardCategory;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}