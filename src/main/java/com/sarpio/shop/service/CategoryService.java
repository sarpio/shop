package com.sarpio.shop.service;

import com.sarpio.shop.model.CategoryEntity;
import com.sarpio.shop.model.dto.CategoryDto;
import com.sarpio.shop.repository.CategoryRepository;
import com.sarpio.shop.utils.EntityDtoMapper;
import com.sarpio.shop.error.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDto addCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = EntityDtoMapper.map(categoryDto);
        categoryRepository.save(categoryEntity);
        return categoryDto;
    }

    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(EntityDtoMapper::map)
                .collect(Collectors.toList());
    }

    public CategoryDto deleteCategory(Long id) throws ResourceNotFoundException {
        if(!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("There is no category with given id: " + id);
        }
        CategoryEntity delCategory = categoryRepository.findById(id).stream()
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Not found category with id: "+id));
        categoryRepository.deleteById(id);
        return EntityDtoMapper.map(delCategory);
    }

    public CategoryDto editCategory(Long id, CategoryDto categoryDto) throws ResourceNotFoundException {
        if(!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("There is no category with given id: " + id);
        }
        CategoryDto dto = CategoryDto.builder().build();
        dto.setId(id);
        dto.setName(categoryDto.getName());
        categoryRepository.save(EntityDtoMapper.map(dto));
        return dto;
    }

    public CategoryDto findCategoryById(Long id) throws ResourceNotFoundException {
        CategoryEntity categoryEntityById = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found category with id: " + id));
        return EntityDtoMapper.map(categoryEntityById);

    }
}
