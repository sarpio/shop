package com.sarpio.shop.service;

import com.sarpio.shop.model.CategoryEntity;
import com.sarpio.shop.model.ProductsEntity;
import com.sarpio.shop.model.dto.ProductsDto;
import com.sarpio.shop.model.dto.post.SaveProductDto;
import com.sarpio.shop.repository.CategoryRepository;
import com.sarpio.shop.repository.ProductsRepository;
import com.sarpio.shop.repository.cache.ProductCache;
import com.sarpio.shop.utils.EntityDtoMapper;
import com.sarpio.shop.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductCache productCache;
    private final CategoryRepository categoryRepository;

    public List<ProductsDto> findAllProducts() {
        return productsRepository.findAll().stream()
                .map(EntityDtoMapper::map)
                .collect(Collectors.toList());
    }

    public ProductsDto findProductById(Long id) throws ResourceNotFoundException {
        Optional<ProductsDto> productResponse = productCache.getProductResponse(id);
        if (productResponse.isPresent()) return productResponse.get();
        ProductsDto productsDto = productsRepository.findById(id).map(EntityDtoMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException(" No product with Id: " + id));
        productCache.saveResponseInCache(productsDto);
        return productsDto;
    }

    public ProductsDto deleteProductById(Long id) throws ResourceNotFoundException {
        ProductsEntity delProduct = productsRepository.findById(id).stream()
                .findFirst().orElseThrow(() ->
                        new ResourceNotFoundException(" You must provide valid id of the product to delete them"));
        productsRepository.deleteById(id);
        productCache.deleteProductResponseFromCahce(id);
        return EntityDtoMapper.map(delProduct);
    }

    public SaveProductDto addProduct(SaveProductDto dto) {
        ProductsEntity entity = EntityDtoMapper.map(dto);
        CategoryEntity category = CategoryEntity.builder().build();
        category.setId(dto.getCategoryId());

        entity.setName(dto.getName());
        if (dto.getName() == null || dto.getName().equals(""))
            throw new ResourceNotFoundException(" Name cannot be null");

        entity.setDescription(dto.getDescription());
        if (dto.getDescription() == null || dto.getDescription().equals(""))
            throw new ResourceNotFoundException("Description cannot be null");

        entity.setPrice(dto.getPrice());
        if (dto.getPrice() == null || dto.getPrice() <= 0)
            throw new ResourceNotFoundException("Price cannot be <=0 or null");

        entity.setCategoryEntity(category);
//        if (dto.getCategoryId() == null || !existsId(dto.getCategoryId()))
//            throw new ResourceNotFoundException("Category Id cannot be null");
//        if (!categoryRepository.existsById(dto.getCategoryId()))
//            throw new ResourceNotFoundException("No valid category id provided");
        productsRepository.save(entity);
        return dto;
    }

    public SaveProductDto editOrAddProduct(Long id, SaveProductDto dto) throws ResourceNotFoundException {
        if (!productsRepository.existsById(id)) {
            throw new ResourceNotFoundException("There is no category with given id: " + id);
        }
        CategoryEntity category = CategoryEntity.builder().build();
        category.setId(dto.getCategoryId());
        ProductsEntity entity = EntityDtoMapper.map(dto);
        if (id > 0) entity.setId(id);
        entity.setName(dto.getName());
        entity.setCategoryEntity(category);
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        productCache.deleteProductResponseFromCahce(id);
        productsRepository.save(entity);
        return dto;
    }

    public boolean existsId(Long id) {
        return categoryRepository.findById(id).isPresent();
    }
}
