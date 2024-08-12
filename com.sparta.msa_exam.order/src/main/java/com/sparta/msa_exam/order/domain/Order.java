package com.sparta.msa_exam.order.domain;

import com.sparta.msa_exam.order.dto.OrderRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long orderId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderProduct> productIds;

    public static Order createOrder(OrderRequestDto orderRequestDto) {
        return Order.builder()
                .name(orderRequestDto.getName())
                .build();
    }
}
