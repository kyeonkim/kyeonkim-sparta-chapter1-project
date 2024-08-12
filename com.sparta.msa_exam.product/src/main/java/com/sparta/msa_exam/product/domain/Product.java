package com.sparta.msa_exam.product.domain;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "supply_price")
    private Integer price;

    public static Product createProduct(ProductRequestDto requestDto) {
        return Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getSupplyPrice())
                .build();
    }
}
