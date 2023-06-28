package com.example.backendservice.service.impl;

import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.exception.ResourceInvalidException;
import com.example.backendservice.exception.ResourceNotFoundException;
import com.example.backendservice.mapper.CategoryMapper;
import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.entity.product.CategoryEntity;
import com.example.backendservice.model.request.CategoryRequest;
import com.example.backendservice.repository.CategoryRepository;
import com.example.backendservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.findCategoryEntityByName(categoryRequest.getName()).isEmpty()) {
            return CategoryMapper.entityToDto(categoryRepository.save(CategoryMapper.requestToEntity(categoryRequest)));
        } else {
            throw new ResourceInvalidException(Constants.CATEGORY + Constants.EXIST);
        }
    }

    @Override
    public void removeCategory(CategoryRequest categoryRequest) {
        categoryRepository.deleteById(categoryRequest.getId());
    }

    @Override
    public CategoryDto findDetailsCategory(Long id) {
        return findCategoryById(id);
    }

    public CategoryDto findCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(Constants.CATEGORY + Constants.NOT_FOUND);
        });
        return CategoryMapper.entityToDto(category);
    }

    @Override
    public List<CategoryDto> findCategoriesByName(String name, Long offset, Long size) {
        return categoryRepository.findCategoriesByText(name, offset, size)
                .stream().map(CategoryMapper::entityToDto).toList();
    }

    @Override
    public Long getSize(String name) {
        return (long) categoryRepository.findSizeByText(name).size();
    }
}
