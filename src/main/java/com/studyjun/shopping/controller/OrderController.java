package com.studyjun.shopping.controller;

import com.studyjun.shopping.dto.request.PaymentRequest;
import com.studyjun.shopping.entity.Order;
import com.studyjun.shopping.service.OrderService;
import com.studyjun.shopping.service.PaymentService;
import com.studyjun.shopping.util.CurrentUser;
import com.studyjun.shopping.util.UserPrincipal;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order & Payment", description = "Order & Payment Logic")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final PaymentService paymentService;
    private final OrderService orderService;

    @PostMapping("/payment")
    private ResponseEntity<?> payment(
            @Parameter(description = "paymentRequest를 입력해주세요.", required = true) @RequestBody PaymentRequest paymentRequest,
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal) {
        try {
            Order order = orderService.getOrderById(paymentRequest.getOrderId());
            return paymentService.processPayment(paymentRequest, order, userPrincipal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment process failed : " + e.getMessage());
        }
    }
}
