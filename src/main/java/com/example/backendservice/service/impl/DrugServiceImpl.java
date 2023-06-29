package com.example.backendservice.service.impl;

import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.exception.ResourceInvalidException;
import com.example.backendservice.exception.ResourceNotFoundException;
import com.example.backendservice.mapper.DrugMapper;
import com.example.backendservice.model.dto.DrugDto;
import com.example.backendservice.model.entity.product.CategoryEntity;
import com.example.backendservice.model.entity.product.DrugEntity;
import com.example.backendservice.model.request.DrugRequest;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.repository.CategoryRepository;
import com.example.backendservice.repository.DrugRepository;
import com.example.backendservice.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DrugServiceImpl implements DrugService {
    private final DrugRepository drugRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<DrugDto> findDrugsByText(FilterRequest filter) {
        return drugRepository
                .findDrugsByText(
                        filter.getKeyRequestText(),
                        filter.getOffset(),
                        filter.getLimit()
                )
                .stream()
                .map(DrugMapper::entityToDto)
                .toList();
    }

    public List<DrugDto> findDrugsByCategory(FilterRequest filter) {
        return drugRepository
                .findDrugsByCategory(
                        filter.getKeyRequestText(),
                        filter.getOffset(),
                        filter.getLimit()
                )
                .stream()
                .map(DrugMapper::entityToDto)
                .toList();
    }

    @Override
    public DrugDto findDrugById(Long id) {
        return DrugMapper.entityToDto(
                drugRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(Constants.DRUG + Constants.NOT_FOUND))
        );
    }

    @Override
    public DrugDto addDrug(DrugRequest request) {
        if (request.getCategoryId() == null)
            throw new ResourceInvalidException(Constants.CATEGORY + Constants.IN_VALID);
        else {
            CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(Constants.CATEGORY + Constants.NOT_FOUND));
            DrugEntity drug = DrugMapper.requestToEntity(request);
            drug.addSelf(category);
            drug.setLastModify(new Timestamp(System.currentTimeMillis()));
            DrugEntity sa = drugRepository.save(drug);
            return DrugMapper.entityToDto(sa);
        }
    }

    @Override
    public DrugDto updateDrug(DrugRequest drug) {
        DrugEntity old = drugRepository.findById(drug.getId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DRUG + Constants.NOT_FOUND));
        DrugEntity newData = DrugMapper.requestToEntity(drug);
        DrugEntity mergedData = old.merge(old, newData);
        return DrugMapper.entityToDto(drugRepository.save(mergedData));
    }

    @Override
    public void removeDrug(Long drugId) {
        drugRepository.deleteById(drugId);
    }

    @Override
    public Long getSize(String text) {
        return (long) drugRepository.findAllDrugsByText(text).size();
    }
}
