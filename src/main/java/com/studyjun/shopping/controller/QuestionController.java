package com.studyjun.shopping.controller;

import com.studyjun.shopping.dto.request.QuestionRequest;
import com.studyjun.shopping.service.QuestionService;
import com.studyjun.shopping.util.CurrentUser;
import com.studyjun.shopping.util.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Question", description = "User writes a question")
@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @Operation(summary = "질문 생성", description = "질문을 작성합니다.")
    @PostMapping("/create")
    private ResponseEntity<?> createQuestion(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "answerRequest를 작성해주세요.", required = true) @RequestBody QuestionRequest questionRequest) {
        return questionService.createQuestion(userPrincipal, questionRequest);
    }

    @Operation(summary = "질문 삭제", description = "질문을 삭제합니다.")
    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteQuestion(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "questionId를 입력해주세요.", required = true) @RequestParam String questionId) {
        return questionService.deleteQuestion(userPrincipal, questionId);
    }

    @Operation(summary = "질문 상세보기", description = "질문을 상세보기합니다.")
    @GetMapping("/detail")
    private ResponseEntity<?> getQuestionDetail(
            @Parameter(description = "questionId를 입력해주세요.", required = true) @RequestParam String questionId) {
        return questionService.getQuestionDetail(questionId);
    }
}