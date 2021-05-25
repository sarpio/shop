package com.sarpio.shop.model;

import jdk.jfr.Description;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class CustomersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    @Description("Regular string not null and not empty")
    @Column(name = "first_name")
    private String first_name;
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    @Column(name = "last_name")
    @Description("Regular string not null and not empty")
    private String last_name;
    @Column(name = "email")
    @Email(message = "must be valid email address")
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    @Description("Must be a valid email address")
    private String email;
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    @Column(name = "phone")
    @Description("use pattern like 555-444-555, cannot be null and not empty")
    private String phone;
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    @Column(name = "address")
    @Description("Complete addrress, mixed string and numbers")
    private String address;

    @OneToMany(mappedBy = "customersEntity", cascade = CascadeType.ALL)
    private Set<OrdersEntity> ordersEntity;
}


