package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.domain.Order;
import com.sparta.msa_exam.order.domain.OrderRepository;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // 주문 추가 비즈니스 로직
    public void createOrder(OrderRequestDto orderRequestDto) {
        orderRepository.save(Order.createOrder(orderRequestDto));
    }
}
