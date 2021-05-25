package com.sarpio.shop.controller;

import com.sarpio.shop.model.dto.CategoryDto;
import com.sarpio.shop.service.CategoryService;
import com.sarpio.shop.error.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sarpio.shop.config.SecurityConfig.*;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('" + SALES_ROLE + "')")
    @Operation(description = "find all categories")
    @GetMapping("/all")
    public List<CategoryDto> listAllCategories() {
        return categoryService.findAllCategories();
    }

    @PreAuthorize("hasRole('" + SALES_ROLE + "')")
    @Operation(description = "find category by ID")
    @GetMapping("{id}")
    public CategoryDto findCategoryById(@PathVariable Long id) throws ResourceNotFoundException {
        return categoryService.findCategoryById(id);
    }

    @PreAuthorize("hasRole('" + MANAGER_ROLE + "')")
    @Operation(description = "add new category")
    @PostMapping
    public CategoryDto addNewCategory(@Valid
                                      @RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @PreAuthorize("hasRole('" + ADMIN_ROLE + "')")
    @Operation(description = "delete category by ID")
    @DeleteMapping("/{id}")
    public CategoryDto deleteCategory(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        return categoryService.deleteCategory(id);
    }

    @PreAuthorize("hasRole('" + MANAGER_ROLE + "')")
    @Operation(description = "update category with specific ID")
    @PutMapping("/{id}")
    public CategoryDto updateCategoryNameById(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto categoryDto) throws ResourceNotFoundException {
        return categoryService.editCategory(id, categoryDto);
    }
}
