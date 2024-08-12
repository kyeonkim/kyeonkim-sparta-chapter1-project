package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final String serverPort;

    public ProductController(ProductService productService, @Value("19093") String serverPort) {
        this.productService = productService;
        this.serverPort = serverPort;
    }

    // 상품 추가
    @PostMapping
    public ResponseEntity<Boolean> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.createProduct(productRequestDto);
        return createResponse(ResponseEntity.ok(true));
    }

    // 상품 조회
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        return createResponse(ResponseEntity.ok(productService.getProducts()));
    }

    // Response Header에 Server-Port를 추가
    public <T> ResponseEntity<T> createResponse(ResponseEntity<T> response) {
        HttpHeaders headers = HttpHeaders.writableHttpHeaders(response.getHeaders());
        headers.set("Server-Port", serverPort);
        return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
    }
}
