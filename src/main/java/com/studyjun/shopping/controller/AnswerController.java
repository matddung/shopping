package com.studyjun.shopping.controller;

import com.studyjun.shopping.dto.request.AnswerRequest;
import com.studyjun.shopping.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/create")
    private ResponseEntity<?> createAnswer(@RequestBody AnswerRequest answerRequest, @RequestParam String questionId) {
        return answerService.createAnswer(answerRequest, questionId);
    }
}
