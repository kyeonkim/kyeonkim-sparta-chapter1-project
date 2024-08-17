package com.sparta.msa_exam.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto implements Serializable {
    private Long productId;
    private String name;
    private Integer price;
}
