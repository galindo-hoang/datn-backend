package com.example.backendservice.service.impl;

import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.exception.ResourceInvalidException;
import com.example.backendservice.exception.ResourceNotFoundException;
import com.example.backendservice.mapper.CategoryMapper;
import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.entity.product.CategoryEntity;
import com.example.backendservice.model.request.CategoryRequest;
import com.example.backendservice.repository.CategoryRepository;
import com.example.backendservice.repository.ImageRepositoryCustom;
import com.example.backendservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ImageRepositoryCustom imageRepositoryCustom;

    @Override
    public CategoryDto addCategory(CategoryRequest categoryRequest) {
        if (categoryRequest.getName().isBlank()) {
            throw new ResourceInvalidException(Constants.CATEGORY + Constants.IN_VALID);
        } else {
            if (categoryRepository.findCategoryEntityByName(categoryRequest.getName()).isEmpty()) {
                CategoryEntity category = CategoryMapper.requestToEntity(categoryRequest);
                try {
                    String imagePath = imageRepositoryCustom.uploadImageBase64Cloudinary(categoryRequest.getImageBase64(), "category", categoryRequest.getName());
                    category.setImage(imagePath);
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

    @Override
    public CategoryDto updateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest.getId() == null || categoryRequest.getId() <= 0) {
            throw new ResourceInvalidException(Constants.CATEGORY + Constants.IN_VALID);
        } else {
            CategoryEntity oldCategory = categoryRepository.findById(categoryRequest.getId()).orElseThrow(() -> new ResourceNotFoundException(Constants.CATEGORY + Constants.NOT_FOUND));
            CategoryEntity parsedCategory = oldCategory.merge(CategoryMapper.requestToEntity(categoryRequest));
            try {
                String image = imageRepositoryCustom.uploadImageBase64Cloudinary(categoryRequest.getImageBase64(), "category", parsedCategory.getName());
                parsedCategory.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!Objects.equals(oldCategory.getName(), parsedCategory.getName())) {
                boolean isExist = categoryRepository.findCategoryEntityByName(parsedCategory.getName()).isPresent();
                if (!isExist) {
                    return CategoryMapper.entityToDto(categoryRepository.save(parsedCategory));
                } else throw new ResourceInvalidException(Constants.NAME + Constants.CATEGORY + Constants.IN_VALID);
            } else {
                return CategoryMapper.entityToDto(categoryRepository.save(parsedCategory));
            }
        }
    }
}
