package com.example.backendservice.model.request;

import com.example.backendservice.common.model.SortType;
import com.example.backendservice.common.utils.TypeFilter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterRequest {
    TypeFilter type;
    String keyRequestText;
    Long keyRequestId;
    Long offset;
    Long limit;
    SortType typeSort;
    String sort;

    Long startDate;
    Long endDate;
}
