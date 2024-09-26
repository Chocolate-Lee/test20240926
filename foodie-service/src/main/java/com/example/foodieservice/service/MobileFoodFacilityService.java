package com.example.foodieservice.service;

import com.example.foodieservice.vo.request.QueryMobileFoodFacilityVO;
import com.example.foodieservice.vo.response.ResponseVO;

public interface MobileFoodFacilityService {

    public ResponseVO queryMobileFoodFacilityByCondition(QueryMobileFoodFacilityVO queryMobileFoodFacilityVO) throws Exception;
}
