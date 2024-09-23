package com.studyjun.shopping.controller;

import com.studyjun.shopping.dto.request.AnswerRequest;
import com.studyjun.shopping.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Answer", description = "Administrator writes a answer")
@RestController
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @Operation(summary = "답변 생성", description = "질문에 대한 답변을 작성합니다.")
    @PostMapping("/create")
    private ResponseEntity<?> createAnswer(
            @Parameter(description = "answerRequest를 작성해주세요.", required = true) @RequestBody AnswerRequest answerRequest,
            @Parameter(description = "questionId를 입력해주세요.", required = true) @RequestParam String questionId) {
        return answerService.createAnswer(answerRequest, questionId);
    }
}