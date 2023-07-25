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
import com.example.backendservice.repository.ImageRepositoryCustom;
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
import java.util.*;

import static com.example.backendservice.common.utils.CodeGeneratorUtils.randomTimeStamp;

@Service
@RequiredArgsConstructor
@Transactional
public class DrugServiceImpl implements DrugService {
    private final DrugRepository drugRepository;
    private final CategoryRepository categoryRepository;
    private final TopSearchRepository topSearchRepository;
    private final ImageRepositoryCustom imageRepositoryCustom;
    private final String folder = "drug";
    @Value("${json.file.rawData}")
    private String filePath;
    @Value("${image.drug.default}")
    private String defaultImage;

    @PostConstruct
    public void hello() {
        imageRepositoryCustom.deleteFolder("drug");
        imageRepositoryCustom.deleteFolder("category");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            for (JsonNode categoryNode : jsonNode) {
                CategoryEntity category = CategoryEntity.builder()
                        .name(categoryNode.has("name") ? categoryNode.get("name").asText() : "")
                        .image(categoryNode.has("icon") ? categoryNode.get("icon").asText() : "")
                        .build();
                category.setDrugs(new ArrayList<>());
                CategoryEntity categoryEntity = categoryRepository.save(category);
                for (JsonNode drugNode : categoryNode.get("drugs")) {
                    if (Objects.equals(drugNode.get("image").asText(), "")) continue;
                    try {
                        DrugRequest request = DrugRequest.builder()
                                .categoryId(categoryEntity.getId())
                                .drugName(drugNode.get("drugName").asText())
                                .price(drugNode.get("price").asLong())
                                .activeIngredient(drugNode.get("interactions").asText())
                                .remarks(drugNode.get("indications").asText())
                                .dosageForms(drugNode.get("dosageForm").asText())
                                .usageDosage(drugNode.get("dosageForm").asText())
                                .label(drugNode.get("label").asText())
                                .build();
                        DrugEntity drug = DrugMapper.requestToEntity(request);
                        drug.setImagePath(drugNode.get("image").asText());
                        drug.addSelf(categoryEntity);
                        drug.setLastModify(randomTimeStamp());
                        drugRepository.save(drug);
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
        List<String> decoratedText = List.of(filter.getKeyRequestText().toLowerCase().split("\\(")[0].split(" "));
        List<DrugEntity> result = new ArrayList<>();
        StringBuilder merSub = new StringBuilder();
        for (String sub : decoratedText) {
            merSub.append(" ").append(sub);
            List<DrugEntity> temp = drugRepository.findDrugsByText(
                    merSub.toString().trim(),
                    filter.getOffset(),
                    filter.getLimit(),
                    filter.getTypeSort(),
                    filter.getSort().equalsIgnoreCase("asc"));
            if (temp.size() == 0) break;
            else {
                result.clear();
                result.addAll(temp);
            }
        }
        return result.stream().map(DrugMapper::entityToDto).toList();
//        return drugRepository
//                .findDrugsByText(
//                        filter.getKeyRequestText(),
//                        filter.getOffset(),
//                        filter.getLimit(),
//                        filter.getTypeSort(),
//                        filter.getSort().equalsIgnoreCase("asc")
//                )
//                .stream()
//                .map(DrugMapper::entityToDto)
//                .toList();
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
            if (drug.getDrugName().isBlank()) throw new ResourceInvalidException(Constants.DRUG + Constants.IN_VALID);
            List<DrugEntity> drugExist = drugRepository.findAllDrugsByText(request.getDrugName());
            if (drugExist.size() != 0) throw new ResourceInvalidException(Constants.DRUG + Constants.EXIST);
            try {
                Map image = imageRepositoryCustom.uploadImageBase64Cloudinary(request.getImageBase64(), folder, drug.getDrugName());
                drug.setImagePath(String.valueOf(image.get("secure_url")));
            } catch (Throwable e) {
                e.printStackTrace();
            }
            drug.addSelf(category);
            drug.setLastModify(new Timestamp(System.currentTimeMillis()));
            DrugEntity sa = drugRepository.save(drug);
            return DrugMapper.entityToDto(sa);
        }
    }

    @Override
    public DrugDto updateDrug(DrugRequest drug) {
        DrugEntity old = drugRepository.findById(drug.getId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DRUG + Constants.NOT_FOUND));
        DrugEntity parsedData = old.merge(DrugMapper.requestToEntity(drug));
        if (drug.getImageBase64() != null && !drug.getImageBase64().isBlank()) {
            try {
                Map image = imageRepositoryCustom.uploadImageBase64Cloudinary(drug.getImageBase64(), folder, parsedData.getDrugName());
                parsedData.setImagePath(String.valueOf(image.get("secure_url")));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return DrugMapper.entityToDto(drugRepository.save(parsedData));
    }

    @Override
    public void removeDrug(DrugRequest request) {
        DrugEntity drug = drugRepository.findDrugEntityById(request.getId()).orElseThrow(() -> new ResourceNotFoundException(Constants.DRUG + Constants.NOT_FOUND));
        drug.removeSelf();
        imageRepositoryCustom.deleteImage(folder, folder + "_" + drug.getDrugName());
        drugRepository.deleteById(request.getId());
    }


    @Override
    public Long getSize(String text) {
        return (long) drugRepository.findAllDrugsByText(text).size();
    }
}
