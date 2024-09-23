package com.studyjun.shopping.service;

import com.studyjun.shopping.entity.Cart;
import com.studyjun.shopping.entity.Product;
import com.studyjun.shopping.entity.User;
import com.studyjun.shopping.repository.CartRepository;
import com.studyjun.shopping.repository.ProductRepository;
import com.studyjun.shopping.repository.UserRepository;
import com.studyjun.shopping.util.DefaultAssert;
import com.studyjun.shopping.util.DefaultAuthenticationException;
import com.studyjun.shopping.util.ErrorCode;
import com.studyjun.shopping.util.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public ResponseEntity<?> addProductInCart(UserPrincipal userPrincipal, String productId) {
        Optional<User> userOptional = userRepository.findByEmail(userPrincipal.getEmail());
        DefaultAssert.isOptionalPresent(userOptional);

        Optional<Product> productOptional = productRepository.findById(productId);
        DefaultAssert.isOptionalPresent(productOptional);

        Optional<Cart> cartOptional = cartRepository.findById(userOptional.get().getId());
        DefaultAssert.isOptionalPresent(cartOptional);

        Cart cart;

        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
            List<String> productList = cart.getProductId();
            productList.add(productId);
            cart.setProductId(productList);
        } else {
            cart = Cart.builder()
                    .userId(userOptional.get().getId())
                    .productId(new ArrayList<>(List.of(productId)))
                    .status("배송 준비 중")
                    .build();
        }

        cartRepository.save(cart);

        return ResponseEntity.ok("Product add success");
    }

    public ResponseEntity<?> deleteProductInCart(UserPrincipal userPrincipal, String productId) {
        Optional<User> userOptional = userRepository.findByEmail(userPrincipal.getEmail());
        DefaultAssert.isOptionalPresent(userOptional);

        Optional<Product> productOptional = productRepository.findById(productId);
        DefaultAssert.isOptionalPresent(productOptional);

        Optional<Cart> cartOptional = cartRepository.findById(userOptional.get().getId());
        DefaultAssert.isOptionalPresent(cartOptional);

        Cart cart;

        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
            List<String> productList = cart.getProductId();
            productList.remove(productId);
            cart.setProductId(productList);
        } else {
            throw new DefaultAuthenticationException(ErrorCode.INVALID_PARAMETER);
        }

        cartRepository.save(cart);

        return ResponseEntity.ok("Product delete success");
    }
}