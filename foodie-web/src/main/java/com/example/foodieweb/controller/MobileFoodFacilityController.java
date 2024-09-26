package com.example.foodieweb.controller;

import com.example.foodieservice.service.MobileFoodFacilityService;
import com.example.foodieservice.vo.request.QueryMobileFoodFacilityVO;
import com.example.foodieservice.vo.response.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/u/foodtruck")
public class MobileFoodFacilityController {

    protected final Logger logger = LoggerFactory.getLogger("MobileFoodFacilityController");

    @Autowired
    private MobileFoodFacilityService mobileFoodFacilityService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseVO getMobileFoodFacilityList(@RequestBody QueryMobileFoodFacilityVO queryMobileFoodFacilityVO) throws Exception {

        try {
            return mobileFoodFacilityService.queryMobileFoodFacilityByCondition(queryMobileFoodFacilityVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception: ", e);
            return ResponseVO.BadResponse("Exception", ResponseVO.STATUS_FAILED);
        }
    }
}
