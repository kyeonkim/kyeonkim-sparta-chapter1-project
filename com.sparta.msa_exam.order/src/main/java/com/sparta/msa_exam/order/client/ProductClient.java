package com.sparta.msa_exam.order.client;

import com.sparta.msa_exam.order.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// FeignClient를 이용하여 product 서비스에서 필요한 서비스 선언
@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/products")
    List<ProductResponseDto> getProducts();
}
