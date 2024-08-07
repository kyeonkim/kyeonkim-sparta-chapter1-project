package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = Product.createProduct(productRequestDto);
        Product savedProduct = productRepository.save(product);
        return toResponseDto(savedProduct);
    }

    private ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getSupply_price()
        );
    }
}
