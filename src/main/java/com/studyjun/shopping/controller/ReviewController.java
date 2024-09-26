package com.studyjun.shopping.controller;

import com.studyjun.shopping.dto.request.ReviewRequest;
import com.studyjun.shopping.service.ReviewService;
import com.studyjun.shopping.util.CurrentUser;
import com.studyjun.shopping.util.UserPrincipal;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/create")
    private ResponseEntity<?> createReview(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "reviewRequest를 작성해주세요.", required = true) @RequestBody ReviewRequest reviewRequest) {
        return reviewService.createReview(userPrincipal, reviewRequest);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteReview(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "reviewId를 입력해주세요.", required = true) @RequestParam String reviewId
    ) {
        return reviewService.deleteReview(userPrincipal, reviewId);
    }
}