package com.studyjun.shopping.service;

import com.studyjun.shopping.dto.request.OrderRequest;
import com.studyjun.shopping.entity.Cart;
import com.studyjun.shopping.entity.Order;
import com.studyjun.shopping.entity.Product;
import com.studyjun.shopping.repository.CartRepository;
import com.studyjun.shopping.repository.OrderRepository;
import com.studyjun.shopping.repository.PaymentRepository;
import com.studyjun.shopping.repository.ProductRepository;
import com.studyjun.shopping.util.DefaultAssert;
import com.studyjun.shopping.util.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public ResponseEntity<?> createOrder(UserPrincipal userPrincipal, OrderRequest orderRequest) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userPrincipal.getId());
        DefaultAssert.isOptionalPresent(cartOptional);

        List<Product> productList = productRepository.findAllById(cartOptional.get().getProductId());

        int totalPrice = productList.stream()
                .mapToInt(Product::getPrice)
                .sum();

        Order order = Order.builder()
                .userId(userPrincipal.getId())
                .productIds(cartOptional.get().getProductId())
                .totalPrice(totalPrice)
                .address(orderRequest.getAddress())
                .status("PENDING")
                .orderedAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }

    public Order getOrderById(String orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        DefaultAssert.isOptionalPresent(orderOptional);

        return orderOptional.get();
    }
}