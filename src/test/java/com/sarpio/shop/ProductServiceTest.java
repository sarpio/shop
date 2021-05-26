package com.sarpio.shop;

import com.sarpio.shop.model.CategoryEntity;
import com.sarpio.shop.model.ProductsEntity;
import com.sarpio.shop.model.dto.ProductsDto;
import com.sarpio.shop.model.dto.post.SaveProductDto;
import com.sarpio.shop.repository.ProductsRepository;
import com.sarpio.shop.repository.cache.ProductCache;
import com.sarpio.shop.service.ProductsService;
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
public class ProductServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @Mock
    private ProductCache productCache;

    @InjectMocks
    private ProductsService productsService;

    @Before
    public void init() {
        Mockito.when(productsRepository.findAll()).thenReturn(prepareProductEntities());
    }

    @Test
    public void shouldProperReturnListOfAllProducts() {
        List<ProductsDto> products = productsService.findAllProducts();
        Assertions.assertNotNull(products);
        Assertions.assertFalse(products.isEmpty());
        Assertions.assertEquals(3, products.size());
    }

    @Test
    public void shouldProperlyFindProductById() {
        //given
        Mockito.when(productCache.getProductResponse(1L)).thenReturn(Optional.empty());
        Mockito.when(productsRepository.findById(1L)).thenReturn(Optional.of(ProductsEntity.builder()
                .id(1L)
                .name("waciki")
                .description("Pozłacane")
                .price(200.22)
                .categoryEntity(CategoryEntity.builder().build())
                .build()));
        //when
        ProductsDto productsDto = productsService.findProductById(1L);
        //then
        Assertions.assertNotNull(productsDto);
        Assertions.assertEquals(1L, productsDto.getId());
        Assertions.assertEquals("waciki", productsDto.getName());
    }

    @Test
    public void shouldProperAddProduct() {
        CategoryEntity categoryEntity = CategoryEntity.builder().build();
        categoryEntity.setId(1L);
        categoryEntity.setName("Category 1");
        ProductsEntity products = ProductsEntity.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(220.01)
                .categoryEntity(categoryEntity)
                .build();
        Mockito.when(productsRepository.save(Mockito.any())).thenReturn(products);
        SaveProductDto saveProductDto = SaveProductDto.builder()
                .id(1L)
                .name("Product 1a")
                .description("Description 1a")
                .price(220.01)
                .categoryId(1L)
                .build();
        SaveProductDto saveProductDto1 = productsService.addProduct(saveProductDto);
        Assertions.assertNotNull(saveProductDto1);
        Assertions.assertEquals("Product 1a", saveProductDto1.getName());
        Assertions.assertEquals(1L, saveProductDto1.getId());
    }

    @Test
    public void shouldProperEditProduct() {
        ProductsEntity productsEntity = ProductsEntity.builder()
                .id(1L)
                .name("waciki")
                .description("Pozłacane")
                .price(200.22)
                .categoryEntity(CategoryEntity.builder()
                        .id(1L)
                        .name("Category 1")
                        .build())
                .build();

        Mockito.when(productsRepository.save(Mockito.any())).thenReturn(productsEntity);
        SaveProductDto saveProductDto = SaveProductDto.builder()
                .id(1L)
                .name("nowa nazwa")
                .description("Pozłacane")
                .price(200.22)
                .categoryId(1L)
                .build();

        ProductsEntity productsEntity1 = EntityDtoMapper.map(productsService.editOrAddProduct(1L, saveProductDto));
        Assertions.assertNotNull(productsEntity1);
        Assertions.assertEquals("nowa nazwa", productsEntity1.getName());
        Assertions.assertEquals(1L, productsEntity1.getId());
    }

    private List<ProductsEntity> prepareProductEntities() {
        List<ProductsEntity> products = new ArrayList<>();
        products.add(ProductsEntity.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(220.01)
                .categoryEntity(prepareCategoryEntities().get(0))
                .build());
        products.add(ProductsEntity.builder()
                .id(2L)
                .name("Product 2")
                .description("Description 2")
                .price(120.01)
                .categoryEntity(prepareCategoryEntities().get(1))
                .build());
        products.add(ProductsEntity.builder()
                .id(3L)
                .name("Product 3")
                .description("Description 3")
                .price(88.12)
                .categoryEntity(prepareCategoryEntities().get(2))
                .build());
        return products;
    }

    private List<CategoryEntity> prepareCategoryEntities() {
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        categoryEntities.add(CategoryEntity.builder()
                .id(1L)
                .name("Category 1")
                .build());
        categoryEntities.add(CategoryEntity.builder()
                .id(2L)
                .name("Category 2")
                .build());
        categoryEntities.add(CategoryEntity.builder()
                .id(3L)
                .name("Category 3")
                .build());
        return categoryEntities;
    }

}
