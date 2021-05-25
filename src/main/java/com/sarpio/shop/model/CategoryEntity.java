package com.sarpio.shop.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @Schema(description = "Enter name of category")
    @NotNull(message = "category name cannot be null")
    @NotBlank(message = "category cannto be blank")
    private String name;

    @OneToMany(mappedBy = "categoryEntity")
    private Set<ProductsEntity> productsEntities;
}
