package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.domain.Product;
import com.sparta.msa_exam.product.domain.ProductRepository;
import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 추가 비즈니스 로직
    @Transactional
    public void createProduct(ProductRequestDto productRequestDto) {
        productRepository.save(Product.createProduct(productRequestDto));
    }

    // 상품 조회 비즈니스 로직
    @Cacheable(cacheNames = "productsCache", key = "getMethodName()")
    public List<ProductResponseDto> getProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponseDto> responseDtoList = new ArrayList<>();

        for (Product product : productList) {
            responseDtoList.add(toResponseDto(product));
        }
        return responseDtoList;
    }

    // 반환 시 상품 데이터 ResponseDto로 변환
    private ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
