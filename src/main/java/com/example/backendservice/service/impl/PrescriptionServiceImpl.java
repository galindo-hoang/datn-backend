package com.example.backendservice.service.impl;

import com.example.backendservice.common.exception.TechnicalException;
import com.example.backendservice.exception.ResourceInvalidException;
import com.example.backendservice.mapper.PrescriptionMapper;
import com.example.backendservice.model.dto.LastUpload;
import com.example.backendservice.model.dto.PrescriptionDto;
import com.example.backendservice.model.entity.prescription.PrescriptionEntity;
import com.example.backendservice.model.request.FilterRequest;
import com.example.backendservice.model.request.PrescriptionRequest;
import com.example.backendservice.repository.ImageRepositoryCustom;
import com.example.backendservice.repository.PrescriptionRepository;
import com.example.backendservice.service.PrescriptionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.backendservice.common.utils.CodeGeneratorUtils.randomTimeStamp;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {
    private final ImageRepositoryCustom imageRepositoryCustom;
    private final PrescriptionRepository prescriptionRepository;
    private final String folder = "prescription";
    @Value("${json.file.prescription}")
    private String filePath;

    @PostConstruct
    private void hello() {
        imageRepositoryCustom.deleteFolder("prescription");
        File directoryPath = new File(filePath);
        List<String> files = List.of(Objects.requireNonNull(directoryPath.list()));
        for (int i = 0; i < files.size()/3; ++i) {
            String fileName = files.get(i);
            PrescriptionEntity prescription = prescriptionRepository.save(PrescriptionEntity.builder().createdOn(randomTimeStamp()).build());
            try {
                File image = new File(filePath + "/" + fileName);
                byte[] fileContent = FileUtils.readFileToByteArray(image);
                String encodeBase64 = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(fileContent);
                Map imageInfo = imageRepositoryCustom.uploadImageBase64Cloudinary(encodeBase64, folder, String.valueOf(prescription.getId()));
                prescriptionRepository.save(addProperty(prescription, imageInfo));
            } catch (Throwable e) {
                prescriptionRepository.delete(prescription);
                e.printStackTrace();
            }
        }
    }


    @Override
    public List<PrescriptionDto> getListImage(FilterRequest filter) {
        return prescriptionRepository.findTopUpload(
                filter.getOffset(),
                filter.getLimit(),
                filter.getSort().equalsIgnoreCase("asc")
        ).stream().map(PrescriptionMapper::entityToDto).toList();
    }

    @Override
    public void deletePrescription(PrescriptionRequest request) {
        imageRepositoryCustom.deleteImage(folder, folder + "_" + request.getId().toString());
        prescriptionRepository.deleteById(request.getId());
    }

    @Override
    public List<LastUpload> listLastUpload(Long startYear, Long startMonth, Long endYear, Long endMonth) {
        String startDate = startYear + "-" + startMonth + "-01 00:00:00";
        String endDate = endYear + "-" + endMonth + "-01 00:00:00";
        List<LastUpload> result = new ArrayList<>();
        prescriptionRepository.analyzePrescription(startDate, endDate).forEach(data -> {
            System.out.println("huy.hoang1 data:" + data.toString());
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM");
                Date date = formatter.parse(data.get(0, String.class));
                result.add(new LastUpload(date.getTime(), data.get(1, Long.class)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        return result.stream().sorted(Comparator.comparingLong(LastUpload::getMonthYear)).toList();
    }

    @Override
    public PrescriptionDto addPrescription(PrescriptionRequest request) {
        if (request.getImageBase64() != null && !request.getImageBase64().isBlank()) {
            PrescriptionEntity prescription = prescriptionRepository.save(new PrescriptionEntity());
            try {
                Map imageInfo = imageRepositoryCustom.uploadImageBase64Cloudinary(request.getImageBase64(), folder, String.valueOf(prescription.getId()));
                return PrescriptionMapper.entityToDto(prescriptionRepository.save(addProperty(prescription, imageInfo)));
            } catch (Throwable e) {
                e.printStackTrace();
                prescriptionRepository.deleteById(prescription.getId());
                throw new TechnicalException(HttpStatus.NOT_IMPLEMENTED, e.getMessage());
            }
        } else throw new ResourceInvalidException("image is invalid");
    }

    @Override
    public Long getSize() {
        return (long) prescriptionRepository.findAll().size();
    }

    private PrescriptionEntity addProperty(PrescriptionEntity prescription, Map imageInfo) {
        prescription.setImagePath(String.valueOf(imageInfo.get("secure_url")));
        prescription.setBytes(Long.valueOf(String.valueOf(imageInfo.get("bytes"))));
        prescription.setWidth(Long.valueOf(String.valueOf(imageInfo.get("width"))));
        prescription.setHeight(Long.valueOf(String.valueOf(imageInfo.get("height"))));
        return prescription;
    }

}
