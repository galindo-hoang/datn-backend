package com.example.backendservice.service.impl;

import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.exception.ResourceInvalidException;
import com.example.backendservice.exception.ResourceNotFoundException;
import com.example.backendservice.mapper.CategoryMapper;
import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.entity.product.CategoryEntity;
import com.example.backendservice.model.request.CategoryRequest;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.repository.CategoryRepository;
import com.example.backendservice.repository.ImageRepositoryCustom;
import com.example.backendservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ImageRepositoryCustom imageRepositoryCustom;
    private final String folder = "category";

    @Override
    public CategoryDto addCategory(CategoryRequest categoryRequest) {
        if (categoryRequest.getName().isBlank()) {
            throw new ResourceInvalidException(Constants.CATEGORY + Constants.IN_VALID);
        } else {
            if (categoryRepository.findCategoryEntityByName(categoryRequest.getName()).isEmpty()) {
                CategoryEntity category = CategoryMapper.requestToEntity(categoryRequest);
                try {
                    Map image = imageRepositoryCustom.uploadImageBase64Cloudinary(categoryRequest.getImageBase64(), folder, categoryRequest.getName());
                    category.setImage(String.valueOf(image.get("secure_url")));
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return CategoryMapper.entityToDto(categoryRepository.save(category));
            } else {
                throw new ResourceInvalidException(Constants.CATEGORY + Constants.EXIST);
            }
        }
    }

    @Override
    public void removeCategory(CategoryRequest categoryRequest) {
        CategoryEntity category = categoryRepository.findById(categoryRequest.getId()).orElseThrow(() -> new ResourceNotFoundException(Constants.CATEGORY + Constants.NOT_FOUND));
        imageRepositoryCustom.deleteImage(folder, folder + "_" + category.getName());
        categoryRepository.deleteById(categoryRequest.getId());
    }

    @Override
    public CategoryDto findDetailsCategory(Long id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.CATEGORY + Constants.NOT_FOUND));
        return CategoryMapper.entityToDto(category);
    }

    @Override
    public List<CategoryDto> findCategoriesByText(FilterRequest filter) {
        return categoryRepository.findCategoriesByText(
                        filter.getKeyRequestText(),
                        filter.getOffset(),
                        filter.getLimit(),
                        filter.getTypeSort(),
                        filter.getSort().equalsIgnoreCase("asc")
                )
                .stream()
                .map(CategoryMapper::entityToDto).toList();
    }

    @Override
    public Long getSize(String name) {
        return (long) categoryRepository.findSizeByText(name).size();
    }

    @Override
    public CategoryDto updateCategory(CategoryRequest categoryRequest) {
        CategoryEntity request = CategoryMapper.requestToEntity(categoryRequest);
        if (request.getId() == null || request.getId() <= 0) {
            throw new ResourceInvalidException(Constants.CATEGORY + Constants.IN_VALID);
        } else {
            CategoryEntity oldCategory = categoryRepository.findById(request.getId()).orElseThrow(() -> new ResourceNotFoundException(Constants.CATEGORY + Constants.NOT_FOUND));
            if (request.getName() != null && !request.getName().isBlank() && !request.getName().equals(oldCategory.getName())) {
                boolean isExist = categoryRepository.findCategoryEntityByName(request.getName()).isPresent();
                if (isExist) {
                    throw new ResourceInvalidException(Constants.NAME + Constants.CATEGORY + Constants.IN_VALID);
                }
            }
            oldCategory.merge(request);
            try {
                Map image = imageRepositoryCustom.uploadImageBase64Cloudinary(categoryRequest.getImageBase64(), folder, oldCategory.getName());
                oldCategory.setImage(String.valueOf(image.get("secure_url")));
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return CategoryMapper.entityToDto(categoryRepository.save(oldCategory));
        }
    }
}
