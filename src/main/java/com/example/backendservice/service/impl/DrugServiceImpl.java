package com.example.backendservice.service.impl;

import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.exception.ResourceInvalidException;
import com.example.backendservice.exception.ResourceNotFoundException;
import com.example.backendservice.mapper.DrugMapper;
import com.example.backendservice.model.dto.DrugDto;
import com.example.backendservice.model.entity.product.CategoryEntity;
import com.example.backendservice.model.entity.product.DrugEntity;
import com.example.backendservice.model.entity.product.TopSearchDrugEntity;
import com.example.backendservice.model.request.DrugRequest;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.repository.CategoryRepository;
import com.example.backendservice.repository.DrugRepository;
import com.example.backendservice.repository.TopSearchRepository;
import com.example.backendservice.service.DrugService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DrugServiceImpl implements DrugService {
    private final DrugRepository drugRepository;
    private final CategoryRepository categoryRepository;
    private final TopSearchRepository topSearchRepository;

    @Value("${json.file.rawData}")
    private String filePath;

    @PostConstruct
    public void hello() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            for (JsonNode categoryNode : jsonNode) {
                CategoryEntity category = CategoryEntity.builder()
                        .name(categoryNode.has("name") ? categoryNode.get("name").asText() : "")
                        .image(categoryNode.has("icon") ? categoryNode.get("icon").asText() : "")
                        .build();
                CategoryEntity categoryEntity = categoryRepository.save(category);
                categoryEntity.setDrugs(new ArrayList<>());
                for (JsonNode drugNode : categoryNode.get("drugs")) {
                    try {

                        DrugRequest request = DrugRequest.builder()
                                .categoryId(categoryEntity.getId())
                                .drugName(drugNode.has("drugName") ? drugNode.get("drugName").asText() : "")
                                .price(drugNode.has("price") ? drugNode.get("price").asLong() : 0)
                                .image(drugNode.has("image") ? drugNode.get("image").asText() : "")
                                .activeIngredient(drugNode.has("interactions") ? drugNode.get("interactions").asText() : "")
                                .remarks(drugNode.has("indications") ? drugNode.get("indications").asText() : "")
                                .dosageForms(drugNode.has("dosageForm") ? drugNode.get("dosageForm").asText() : "")
                                .usageDosage(drugNode.has("dosageForm") ? drugNode.get("dosageForm").asText() : "")
                                .label(drugNode.has("label") ? drugNode.get("label").asText() : "")
                                .build();
                        DrugEntity drug = DrugMapper.requestToEntity(request);
                        drug.addSelf(categoryEntity);
                        drug.setLastModify(new Timestamp(System.currentTimeMillis()));
                        DrugEntity sa = drugRepository.save(drug);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DrugDto> findDrugsByText(FilterRequest filter) {
        return drugRepository
                .findDrugsByText(
                        filter.getKeyRequestText(),
                        filter.getOffset(),
                        filter.getLimit(),
                        filter.getTypeSort(),
                        filter.getSort().equalsIgnoreCase("asc")
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
                        filter.getLimit(),
                        filter.getTypeSort(),
                        filter.getSort().equalsIgnoreCase("asc")
                )
                .stream()
                .map(DrugMapper::entityToDto)
                .toList();
    }

    @Override
    public List<DrugDto> findTopSearchDrugs(FilterRequest filter) {
        return drugRepository.findTopSearch(
                filter.getOffset(),
                filter.getLimit(),
                filter.getSort().equalsIgnoreCase("asc")
        ).stream().map(data -> {
            DrugDto drug = DrugMapper.entityToDto(Objects.requireNonNull(data.get(0, DrugEntity.class)));
            drug.setNumberSearch(data.get(1, Long.class));
            drug.setLastSearch(Objects.requireNonNull(data.get(2, Timestamp.class)).getTime());
            return drug;
        }).toList();
    }

    @Override
    public DrugDto findDrugById(Long id) {
        DrugEntity drug = drugRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.DRUG + Constants.NOT_FOUND));
        Optional<TopSearchDrugEntity> optionalTopSearchDrug = topSearchRepository.findTopSearchDrugEntityByDrug_Id(id);
        TopSearchDrugEntity topSearchDrug;
        if (optionalTopSearchDrug.isPresent()) {
            topSearchDrug = optionalTopSearchDrug.get();
            topSearchDrug.setCount(1 + topSearchDrug.getCount());
        } else {
            topSearchDrug = TopSearchDrugEntity.builder()
                    .lastSearch(new Timestamp(System.currentTimeMillis()))
                    .count(1L)
                    .drug(drug)
                    .build();
        }
        topSearchRepository.save(topSearchDrug);
        return DrugMapper.entityToDto(drug);
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
