package com.example.backendservice.service.impl;

import com.example.backendservice.common.utils.Constants;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DrugServiceImpl implements DrugService {
    private final DrugMapper drugMapper;
    private final DrugRepository drugRepository;

    @Override
    public List<DrugEntity> findDrugsByText(FilterRequest filter) {
        int size = filter.getSize() != null ? filter.getSize() : 20;
        int offset = filter.getOffset() != null ? filter.getOffset() * size : 0;
        return drugRepository.findDrugsByText(filter.getText(), offset, size);
    }

    @Override
    public DrugDto findDrugById(Long id) {
        return drugMapper.entityToDto(drugRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(Constants.DRUG + Constants.NOT_FOUND);
        }));
    }

    @Override
    public DrugDto addDrug(DrugRequest request) {
        DrugEntity drug = drugMapper.requestToEntity(request);
        drug.setLastModify(new Timestamp(System.currentTimeMillis()));
        return drugMapper.entityToDto(drugRepository.save(drug));
    }

    @Override
    public DrugDto updateDrug(DrugRequest drug) {
        DrugEntity old = drugRepository.findById(drug.getId()).orElseThrow(() -> {
            throw new ResourceNotFoundException(Constants.DRUG + Constants.NOT_FOUND);
        });
        DrugEntity newData = drugMapper.requestToEntity(drug);
        DrugEntity mergedData = old.merge(old, newData);
        return drugMapper.entityToDto(drugRepository.save(mergedData));
    }

    @Override
    public void removeDrug(Long drugId) {
        drugRepository.deleteById(drugId);
    }
}
