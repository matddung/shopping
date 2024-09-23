package com.studyjun.shopping.service;

import com.studyjun.shopping.dto.request.QuestionRequest;
import com.studyjun.shopping.entity.Question;
import com.studyjun.shopping.entity.User;
import com.studyjun.shopping.repository.QuestionRepository;
import com.studyjun.shopping.repository.UserRepository;
import com.studyjun.shopping.util.DefaultAssert;
import com.studyjun.shopping.util.DefaultAuthenticationException;
import com.studyjun.shopping.util.ErrorCode;
import com.studyjun.shopping.util.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> createQuestion(UserPrincipal userPrincipal, QuestionRequest questionRequest) {
        Question question = Question.builder()
                .subject(questionRequest.getSubject())
                .content(questionRequest.getContent())
                .userId(userPrincipal.getId())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        
        questionRepository.save(question);

        return ResponseEntity.ok("Question create success");
    }

    public ResponseEntity<?> modifyQuestion(UserPrincipal userPrincipal, QuestionRequest questionRequest, String questionId) {
        Optional<User> userOptional = userRepository.findByEmail(userPrincipal.getEmail());
        DefaultAssert.isOptionalPresent(userOptional);

        Optional<Question> questionOptional = questionRepository.findById(questionId);
        DefaultAssert.isOptionalPresent(questionOptional);

        Question question = questionOptional.get();

        if (!userOptional.get().getId().equals(question.getUserId())) {
            throw new DefaultAuthenticationException(ErrorCode.INVALID_PARAMETER);
        }

        if (questionRequest.getSubject() != null) {
            question.setSubject(questionRequest.getSubject());
        }
        if (questionRequest.getContent() != null) {
            question.setContent(questionRequest.getContent());
        }

        question.setModifiedAt(LocalDateTime.now());

        questionRepository.save(question);

        return ResponseEntity.ok("Question modify success");
    }

    public ResponseEntity<?> deleteQuestion(UserPrincipal userPrincipal, String questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        DefaultAssert.isOptionalPresent(questionOptional);

        if (questionOptional.get().getId().equals(userPrincipal.getEmail())) {
            throw new DefaultAuthenticationException(ErrorCode.INVALID_PARAMETER);
        }

        questionRepository.delete(questionOptional.get());

        return ResponseEntity.ok("Question delete success");
    }

    public ResponseEntity<?> getQuestionDetail(String questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        DefaultAssert.isOptionalPresent(questionOptional);

        return ResponseEntity.ok(questionOptional.get());
    }
}