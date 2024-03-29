package com.example.backendservice.service.impl;

import com.example.backendservice.common.exception.TechnicalException;
import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.exception.ResourceInvalidException;
import com.example.backendservice.exception.ResourceNotFoundException;
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
import java.sql.Timestamp;
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

//    @PostConstruct
    private void hello() {
        imageRepositoryCustom.deleteFolder("prescription");
        File directoryPath = new File(filePath);
        List<String> files = List.of(Objects.requireNonNull(directoryPath.list()));
        for (int i = 200; i < 300; ++i) {
            String fileName = files.get(i);
            long rate = new Random().nextLong(6);
            PrescriptionEntity prescription = prescriptionRepository.save(
                    PrescriptionEntity.builder()
                            .createdOn(randomTimeStamp())
                            .rate(rate)
                            .build()
            );
            if (rate == 0) prescription.setRate(null);
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
        String endDate = endYear + "-" + (endMonth + 1) + "-01 00:00:00";
        if(endMonth == 12) endDate = (endYear + 1) + "-01-01 00:00:00";
        List<LastUpload> result = new ArrayList<>();
        prescriptionRepository.analyzePrescription(startDate, endDate).forEach(data -> {
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM");
                Date date = formatter.parse(data.get(0, String.class));
                result.add(new LastUpload(date.getTime(), data.get(1, Long.class), data.get(2, Long.class)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        return result.stream().sorted(Comparator.comparingLong(LastUpload::getMonthYear)).toList();
    }

    @Override
    public PrescriptionDto addPrescription(PrescriptionRequest request) {
        if (request.getImageBase64() != null && !request.getImageBase64().isBlank()) {
            PrescriptionEntity entity = new PrescriptionEntity();
            entity.setRate(request.getRate());
            entity.setReview(request.getReview());
            PrescriptionEntity prescription = prescriptionRepository.save(entity);
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

    @Override
    public PrescriptionDto getById(Long id) {
        PrescriptionEntity prescription = prescriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prescription " + Constants.NOT_FOUND));
        return PrescriptionMapper.entityToDto(prescription);
    }

    @Override
    public Map<String, Long> analyzeRate(Integer month, Integer year) {
        if (month > 0 && month <= 12) {
            HashMap<String, Long> decoratedStar = new HashMap<>();
            HashMap<Long, Long> rawStar = new HashMap<>();
            rawStar.put(1L, 0L);
            rawStar.put(2L, 0L);
            rawStar.put(3L, 0L);
            rawStar.put(4L, 0L);
            rawStar.put(5L, 0L);
            try {
                prescriptionRepository.analyzeRateByMonth(month, year).forEach(data -> {
                    Long star = data.get(1, Long.class);
                    if (star != null) {
                        rawStar.put(star, data.get(2, Long.class));
                    }
                });
            } catch (Throwable e) {
                e.printStackTrace();
            }
            rawStar.forEach((key, value) -> {
                decoratedStar.put("star" + key, value);
            });
            return decoratedStar;
        } else {
            throw new ResourceInvalidException("month " + Constants.IN_VALID);
        }
    }

    @Override
    public Map<String, Long> listLastUploadStar(List<Long> months) {
        HashMap<String, Long> decoratedStar = new HashMap<>();
        HashMap<Long, Long> rawStar = new HashMap<>();
        rawStar.put(1L, 0L);
        rawStar.put(2L, 0L);
        rawStar.put(3L, 0L);
        rawStar.put(4L, 0L);
        rawStar.put(5L, 0L);
        months.forEach(timestamp -> {
            Timestamp ts = new Timestamp(timestamp);
            Calendar cal = Calendar.getInstance();
            cal.setTime(ts);
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            try {
                prescriptionRepository.analyzeRateByMonth(month, year).forEach(data -> {
                    Long star = data.get(1, Long.class);
                    Long number = data.get(2, Long.class);
                    if (star != null && number != null) {
                        rawStar.put(star, rawStar.get(star) + number);
                    }
                });
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
        rawStar.forEach((key, value) -> {
            decoratedStar.put("star" + key, value);
        });
        return decoratedStar;
    }

    private PrescriptionEntity addProperty(PrescriptionEntity prescription, Map imageInfo) {
        prescription.setImagePath(String.valueOf(imageInfo.get("secure_url")));
        prescription.setBytes(Long.valueOf(String.valueOf(imageInfo.get("bytes"))));
        prescription.setWidth(Long.valueOf(String.valueOf(imageInfo.get("width"))));
        prescription.setHeight(Long.valueOf(String.valueOf(imageInfo.get("height"))));
        return prescription;
    }

}
