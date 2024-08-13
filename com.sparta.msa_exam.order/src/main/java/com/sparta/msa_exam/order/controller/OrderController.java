package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.dto.OrderProductRequestDto;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private final String serverPort;

    public OrderController(OrderService orderService, @Value("19092") String serverPort) {
        this.orderService = orderService;
        this.serverPort = serverPort;
    }

    // 주문 추가
    @PostMapping
    public ResponseEntity<Boolean> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.createOrder(orderRequestDto);
        return createResponse(ResponseEntity.ok(true));
    }

    // 주문에 상품 추가
    @PutMapping("/{orderId}")
    public ResponseEntity<Boolean> addProductToOrder(
            @PathVariable(name = "orderId") Long orderId,
            @RequestBody OrderProductRequestDto orderProductRequestDto
            ) {
        orderService.addProductToOrder(orderId, orderProductRequestDto.getProductId());
        return createResponse(ResponseEntity.ok(true));
    }

    // Response Header에 Server-Port를 추가
    public <T> ResponseEntity<T> createResponse(ResponseEntity<T> response) {
        HttpHeaders headers = HttpHeaders.writableHttpHeaders(response.getHeaders());
        headers.set("Server-Port", serverPort);
        return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
    }
}
