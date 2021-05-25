package com.sarpio.shop.controller;

import com.sarpio.shop.model.dto.ProductsDto;
import com.sarpio.shop.model.dto.post.SaveProductDto;
import com.sarpio.shop.repository.cache.ProductCache;
import com.sarpio.shop.service.ProductsService;
import com.sarpio.shop.error.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.sarpio.shop.config.SecurityConfig.*;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {

    ProductsService productsService;
    ProductCache productCache;

    @PreAuthorize("hasRole('" + SALES_ROLE + "')")
    @Operation(description = "List all products")
    @GetMapping
    public List<ProductsDto> listAllProducts() {
        List<ProductsDto> allProducts = productsService.findAllProducts();
        return allProducts;
    }

    @PreAuthorize("hasRole('" + SALES_ROLE + "')")
    @Operation(description = "List product by Id")
    @GetMapping("{id}")
    public ProductsDto findProductsById(@PathVariable(name = "id") Long id) throws InterruptedException, ResourceNotFoundException {
        Optional<ProductsDto> productResponse = productCache.getProductResponse(id);
        if (productResponse.isPresent()) return productResponse.get();
        Thread.sleep(3000);
        return productsService.findProductById(id);
    }

    @PreAuthorize("hasRole('" + MANAGER_ROLE + "')")
    @Operation(description = "Add new product")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SaveProductDto addNewProduct(@Valid @RequestBody SaveProductDto dto) {
        SaveProductDto saveProductsDto = productsService.addProduct(dto);
        return saveProductsDto;
    }

    @PreAuthorize("hasRole('" + ADMIN_ROLE + "')")
    @Operation(description = "Delete product by Id")
    @DeleteMapping("{id}")
    public ProductsDto deleteProduct(@PathVariable Long id) throws ResourceNotFoundException {
        return productsService.deleteProductById(id);
    }

    @PreAuthorize("hasRole('" + MANAGER_ROLE + "')")
    @Operation(description = "Edit product by Id")
    @PutMapping("{id}")
    public SaveProductDto updateProductsById(
            @PathVariable Long id,
            @Valid @RequestBody SaveProductDto saveDto) throws ResourceNotFoundException {
        return productsService.editOrAddProduct(id, saveDto);
    }
}

