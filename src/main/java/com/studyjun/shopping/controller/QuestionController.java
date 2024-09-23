package com.studyjun.shopping.controller;

import com.studyjun.shopping.dto.request.QuestionRequest;
import com.studyjun.shopping.service.QuestionService;
import com.studyjun.shopping.util.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/create")
    private ResponseEntity<?> createQuestion(UserPrincipal userPrincipal, @RequestBody QuestionRequest questionRequest) {
        return questionService.createQuestion(userPrincipal, questionRequest);
    }

    @PatchMapping("/modify")
    private ResponseEntity<?> modifyQuestion(UserPrincipal userPrincipal, @RequestBody QuestionRequest questionRequest, @RequestParam String questionId) {
        return questionService.modifyQuestion(userPrincipal, questionRequest, questionId);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteQuestion(UserPrincipal userPrincipal, @RequestParam String questionId) {
        return questionService.deleteQuestion(userPrincipal, questionId);
    }

    @GetMapping("/detail")
    private ResponseEntity<?> getQuestionDetail(@RequestParam String questionId) {
        return questionService.getQuestionDetail(questionId);
    }
}