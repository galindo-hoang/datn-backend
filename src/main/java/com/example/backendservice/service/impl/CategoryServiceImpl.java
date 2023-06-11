package com.example.backendservice.service.impl;

import com.example.backendservice.mapper.CategoryMapper;
import com.example.backendservice.model.DrugK;
import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.entity.product.CategoryEntity;
import com.example.backendservice.model.request.CategoryRequest;
import com.example.backendservice.repository.CategoryRepository;
import com.example.backendservice.service.CategoryService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final Environment env;

    @Override
    public CategoryDto addCategory(CategoryRequest categoryRequest) {
        System.out.println(categoryRequest);
        CategoryEntity entity = categoryRepository.save(CategoryMapper.requestToEntity(categoryRequest));
        return CategoryMapper.entityToDto(entity);
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
