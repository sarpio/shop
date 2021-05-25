package com.sarpio.shop.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "products")
public class ProductsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Automatically generated value")
    private Long id;

    @NotBlank(message = "Cannot be blank")
    @Schema(description = "Product name", required = true)
    @Column(name = "name")
    private String name;

    @Schema(description = "Product name", required = true)
    @Column(name = "description")
    private String description;

    @Column(name = "price")
    @Schema(description = "Product price", required = true)
    private Double price;

    @Schema(description = "Order details reffering to specific product")
    @OneToMany(mappedBy = "productsEntity")
    private Set<OrderDetailEntity> orderDetailEntity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;
}
