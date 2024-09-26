package com.example.foodieservice.vo.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryMobileFoodFacilityVO {
    private Double latitude;
    private Double longitude;
    private String food;

}
