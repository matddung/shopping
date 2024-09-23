package com.studyjun.shopping.service;

import com.studyjun.shopping.dto.request.AnswerRequest;
import com.studyjun.shopping.entity.Answer;
import com.studyjun.shopping.entity.Question;
import com.studyjun.shopping.repository.AnswerRepository;
import com.studyjun.shopping.repository.QuestionRepository;
import com.studyjun.shopping.util.DefaultAssert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public ResponseEntity<?> createAnswer(AnswerRequest answerRequest, String questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        DefaultAssert.isOptionalPresent(questionOptional);

        Answer answer = Answer.builder()
                .questionId(questionId)
                .content(answerRequest.getContent())
                .build();

        answerRepository.save(answer);

        return ResponseEntity.ok("Answer create success");
    }
}