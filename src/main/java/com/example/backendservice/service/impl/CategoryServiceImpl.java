package com.example.backendservice.service.impl;

import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.request.CategoryRequest;
import com.example.backendservice.repository.CategoryRepository;
import com.example.backendservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(CategoryRequest categoryRequest) {
        return null;
    }

    @Override
    public void removeCategory(CategoryRequest categoryRequest) {

    }

    @Override
    public CategoryDto findDetailsCategory(Map<String, Object> request) {
        if (request.containsKey("id")) {
            return this.findCategoryById((Long) request.get("id"));
        } else if (request.containsKey("name")) {
            return this.findCategoryByName((String) request.get("name"));
        } else throw new IllegalArgumentException("arguments is not enough!!");
    }

    @Override
    public List<CategoryDto> findListCategories(Long offSet, Long limit) {
        return null;
    }

    public CategoryDto findCategoryById(Long id) {
        return null;
    }

    public CategoryDto findCategoryByName(String name) {
        return null;
    }
}
