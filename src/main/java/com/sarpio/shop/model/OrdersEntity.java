package com.sarpio.shop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "orders")
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Long number;

    @Column(name = "date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrdersStatus status;

    @Column(name = "total")
    private Double total;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomersEntity customersEntity;

    @OneToMany(mappedBy = "ordersEntity", cascade = CascadeType.ALL)
    Set<OrderDetailEntity> orderDetailEntities;
}
