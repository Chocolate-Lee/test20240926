package com.example.foodieservice.impl;

import com.example.foodiedata.mapper.MobileFoodFacilityMapper;
import com.example.foodiedata.model.MobileFoodFacilityModel;
import com.example.foodiedata.querymodel.QueryMobileFoodFacilityModel;
import com.example.foodieservice.service.MobileFoodFacilityService;
import com.example.foodieservice.vo.request.QueryMobileFoodFacilityVO;
import com.example.foodieservice.vo.response.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MobileFoodFacilityServiceImpl implements MobileFoodFacilityService {

    protected final Logger logger = LoggerFactory.getLogger("MobileFoodFacilityService");

    @Autowired
    private MobileFoodFacilityMapper mobileFoodFacilityMapper;

    @Override
    public ResponseVO queryMobileFoodFacilityByCondition(QueryMobileFoodFacilityVO queryMobileFoodFacilityVO) throws Exception {

        QueryMobileFoodFacilityModel query = new QueryMobileFoodFacilityModel();
        query.setFood(queryMobileFoodFacilityVO.getFood());
        query.setLatitude(queryMobileFoodFacilityVO.getLatitude());
        query.setLongitude(queryMobileFoodFacilityVO.getLongitude());

        List<MobileFoodFacilityModel> list = mobileFoodFacilityMapper.selectMobileFoodFacilityByCondition(query);

        int showDistance = 0;
        if (queryMobileFoodFacilityVO.getLatitude() != null && queryMobileFoodFacilityVO.getLongitude() != null) {
            showDistance = 1;
        }

        ResponseVO respVO = new ResponseVO();
        respVO.setStatus(ResponseVO.STATUS_SUCCESSED);
        respVO.setFoodTruckList(list);
        respVO.setShowDistance(showDistance);

        return respVO;
    }

}
