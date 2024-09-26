package com.studyjun.shopping.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.studyjun.shopping.dto.request.PaymentRequest;
import com.studyjun.shopping.entity.Order;
import com.studyjun.shopping.entity.Product;
import com.studyjun.shopping.repository.OrderRepository;
import com.studyjun.shopping.repository.PaymentRepository;
import com.studyjun.shopping.repository.ProductRepository;
import com.studyjun.shopping.util.UserPrincipal;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private IamportClient iamportClient;

    @Value("${iamport.api-key}")
    private String apiKey;

    @Value("${iamport.secret}")
    private String secretKey;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    private IamportResponse<Payment> verifyPayment(String impUid) throws Exception {
        return iamportClient.paymentByImpUid(impUid);
    }

    public ResponseEntity<?> processPayment(PaymentRequest paymentRequest, Order order, UserPrincipal userPrincipal) throws Exception {
        IamportResponse<Payment> paymentResponse = verifyPayment(paymentRequest.getImpUid());
        Payment paymentData = paymentResponse.getResponse();

        try {
            if (paymentData.getStatus().equals("paid") && paymentData.getAmount().compareTo(BigDecimal.valueOf(order.getTotalPrice())) == 0) {
                com.studyjun.shopping.entity.Payment payment = com.studyjun.shopping.entity.Payment.builder()
                        .orderId(order.getId())
                        .userId(userPrincipal.getId())
                        .amount(order.getTotalPrice())
                        .status("PAID")
                        .method(paymentRequest.getPaymentMethod().toUpperCase())
                        .transactionId(paymentRequest.getImpUid())
                        .paymentAt(LocalDateTime.now())
                        .build();

                paymentRepository.save(payment);

                order.setStatus("COMPLETED");
                orderRepository.save(order);

                decreaseStockQuantity(order.getProductIds());

                return ResponseEntity.ok("Payment process success");
            } else {
                order.setStatus("CANCELLED");
                orderRepository.save(order);
                return ResponseEntity.badRequest().body("Payment process failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during payment verification : " + e.getMessage());
        }
    }

    private void decreaseStockQuantity(List<String> productIds) {
        List<Product> products = productRepository.findAllById(productIds);

        for (Product product : products) {
            if (product.getStockQuantity() > 0) {
                product.setStockQuantity(product.getStockQuantity() - 1);
                productRepository.save(product);
            } else {
                throw new RuntimeException("Product out of stock: " + product.getName());
            }
        }
    }
}