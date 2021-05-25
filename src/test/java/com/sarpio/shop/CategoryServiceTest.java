package com.sarpio.shop;

import com.sarpio.shop.model.CategoryEntity;
import com.sarpio.shop.model.dto.CategoryDto;
import com.sarpio.shop.repository.CategoryRepository;
import com.sarpio.shop.service.CategoryService;
import com.sarpio.shop.utils.EntityDtoMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Before
    public void init() {
        Mockito.when(categoryRepository.findAll()).thenReturn(prepareCategoryEntities());
    }

    @Test
    public void shouldProperReturnListOfCategories() {
        List<CategoryDto> categories = categoryService.findAllCategories();
        Assertions.assertNotNull(categories);
        Assertions.assertFalse(categories.isEmpty());
        Assertions.assertEquals(3, categories.size());
    }

    @Test
    public void shouldProperFindCategoryById() {
        //given
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(CategoryEntity.builder()
                .id(1L)
                .name("Category 1")
                .build()));
        //when
        CategoryDto categoryDto = categoryService.findCategoryById(1L);
        //then
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(1L, categoryDto.getId());
        Assertions.assertEquals("Category 1", categoryDto.getName());
    }

    @Test
    public void shouldProperAddNewCategory() {
        CategoryEntity category = CategoryEntity.builder()
                .id(1L)
                .name("Category 1")
                .build();
//        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        CategoryDto categoryDto = categoryService.addCategory(EntityDtoMapper.map(category));
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(1L, categoryDto.getId());
        Assertions.assertEquals("Category 1", categoryDto.getName());
    }

    @Test
    public void shouldProperEditCategory() {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("Category 1")
                .build();
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(categoryEntity);
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("new Category")
                .build();
        CategoryDto categoryDto1 = categoryService.editCategory(1L, categoryDto);
        Assertions.assertNotNull(categoryDto1);
        Assertions.assertEquals("new Category", categoryDto1.getName());
        Assertions.assertEquals(1L, categoryDto1.getId());


    }

    public List<CategoryEntity> prepareCategoryEntities() {
        List<CategoryEntity> categories = new ArrayList<>();
        categories.add(CategoryEntity.builder()
                .id(1L)
                .name("Category 1")
                .build());
        categories.add(CategoryEntity.builder()
                .id(2L)
                .name("Category 2")
                .build());
        categories.add(CategoryEntity.builder()
                .id(3L)
                .name("Category 3")
                .build());
        return categories;
    }
}
