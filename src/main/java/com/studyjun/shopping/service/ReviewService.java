package com.studyjun.shopping.service;

import com.studyjun.shopping.dto.request.ReviewRequest;
import com.studyjun.shopping.entity.Order;
import com.studyjun.shopping.entity.Review;
import com.studyjun.shopping.entity.User;
import com.studyjun.shopping.repository.OrderRepository;
import com.studyjun.shopping.repository.ReviewRepository;
import com.studyjun.shopping.repository.UserRepository;
import com.studyjun.shopping.util.DefaultAssert;
import com.studyjun.shopping.util.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public ResponseEntity<?> createReview(UserPrincipal userPrincipal, ReviewRequest reviewRequest) {
        Optional<User> userOptional = userRepository.findById(userPrincipal.getId());
        DefaultAssert.isOptionalPresent(userOptional);

        Optional<Order> orderOptional = orderRepository.findByUserIdAndProductIds(userPrincipal.getId(), reviewRequest.getProductId());
        DefaultAssert.isOptionalPresent(orderOptional);

        Review review = Review.builder()
                .userId(userOptional.get().getId())
                .productId(reviewRequest.getProductId())
                .rating(reviewRequest.getRating())
                .reviewText(reviewRequest.getReviewText())
                .build();

        reviewRepository.save(review);

        return ResponseEntity.ok("Review create success");
    }

    public ResponseEntity<?> deleteReview(UserPrincipal userPrincipal, String reviewId) {
        Optional<User> userOptional = userRepository.findById(userPrincipal.getId());
        DefaultAssert.isOptionalPresent(userOptional);

        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        DefaultAssert.isOptionalPresent(reviewOptional);

        if (reviewOptional.get().getUserId() == userOptional.get().getId()) {
            reviewRepository.delete(reviewOptional.get());

            return ResponseEntity.ok("Review delete success");
        }

        return ResponseEntity.badRequest().body("Review delete failed");
    }
}