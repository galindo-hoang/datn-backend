package com.example.backendservice.service.impl;

import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.common.utils.TypeFilter;
import com.example.backendservice.exception.ResourceNotFoundException;
import com.example.backendservice.mapper.DrugMapper;
import com.example.backendservice.model.dto.DrugDto;
import com.example.backendservice.model.entity.product.DrugEntity;
import com.example.backendservice.model.request.DrugRequest;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.repository.DrugRepository;
import com.example.backendservice.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class DrugServiceImpl implements DrugService {
    private final DrugRepository drugRepository;

    public List<DrugDto> findDrugsByText(FilterRequest filter) {
        Long offset = filter.getOffset() * filter.getLimit();
        return drugRepository
                .findDrugsByText(
                        filter.getKeyRequestText(),
                        offset,
                        filter.getLimit()
                )
                .stream()
                .map(DrugMapper::entityToDto)
                .toList();
    }

    public List<DrugDto> findsDrugByCategory(FilterRequest filter) {
        Long offset = filter.getOffset() * filter.getLimit();
        return drugRepository
                .findDrugsByCategory(
                        filter.getKeyRequestId(),
                        offset,
                        filter.getLimit()
                )
                .stream()
                .map(DrugMapper::entityToDto)
                .toList();
    }

    @Override
    public List<DrugDto> findDrugsByFilter(Map<String, Object> rawFilter) {
        FilterRequest filterRequest;
        Long offset = rawFilter.get("offset") == null ? 0 : (Long) rawFilter.get("offset");
        Long limit = rawFilter.get("limit") == null ? 20 : (Long) rawFilter.get("offset");
        if (rawFilter.get("text") == null) {
            filterRequest = FilterRequest.builder()
                    .type(TypeFilter.DRUG_BY_CATEGORY)
                    .keyRequestId((Long) rawFilter.get("categoryId"))
                    .offset(offset)
                    .limit(limit)
                    .build();
        } else {
            filterRequest = FilterRequest.builder()
                    .type(TypeFilter.DRUG_BY_TEXT)
                    .keyRequestText((String) rawFilter.get("text"))
                    .offset(offset)
                    .limit(limit)
                    .build();
        }

        switch (filterRequest.getType()) {
            case DRUG_BY_TEXT -> {
                return this.findDrugsByText(filterRequest);
            }
            case DRUG_BY_CATEGORY -> {
                return this.findsDrugByCategory(filterRequest);
            }
            default -> {
                return Collections.emptyList();
            }
        }
    }

    @Override
    public DrugDto findDrugById(Long id) {
        return DrugMapper.entityToDto(drugRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(Constants.DRUG + Constants.NOT_FOUND);
        }));
    }

    @Override
    public DrugDto addDrug(DrugRequest request) {
        DrugEntity drug = DrugMapper.requestToEntity(request);
        drug.setLastModify(new Timestamp(System.currentTimeMillis()));
        return DrugMapper.entityToDto(drugRepository.save(drug));
    }

    @Override
    public DrugDto updateDrug(DrugRequest drug) {
        DrugEntity old = drugRepository.findById(drug.getId()).orElseThrow(() -> {
            throw new ResourceNotFoundException(Constants.DRUG + Constants.NOT_FOUND);
        });
        DrugEntity newData = DrugMapper.requestToEntity(drug);
        DrugEntity mergedData = old.merge(old, newData);
        return DrugMapper.entityToDto(drugRepository.save(mergedData));
    }

    @Override
    public void removeDrug(Long drugId) {
        drugRepository.deleteById(drugId);
    }
}
