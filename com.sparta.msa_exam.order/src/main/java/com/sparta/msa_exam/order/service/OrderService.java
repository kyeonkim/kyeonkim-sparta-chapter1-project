package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.domain.Order;
import com.sparta.msa_exam.order.domain.OrderProduct;
import com.sparta.msa_exam.order.domain.OrderRepository;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    // 주문 추가 비즈니스 로직
    public void createOrder(OrderRequestDto orderRequestDto) {
        orderRepository.save(Order.createOrder(orderRequestDto));
    }

    // 주문에 상품 추가 비즈니스 로직
    public void addProductToOrder(Long orderId, Long productId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Not Found."));
        // 상품이 있는 지 확인
        productClient.getProducts().stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));
        // 주문에 상품 추가
        order.addProduct(OrderProduct.createOrderProduct(order, productId));
    }
}
