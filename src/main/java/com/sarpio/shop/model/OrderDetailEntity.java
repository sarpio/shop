package com.sarpio.shop.model;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "orderDetailEntity")
@Table(name = "order_detail")
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "total_price")
    private Double total_price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductsEntity productsEntity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrdersEntity ordersEntity;
}
