package com.example.foodieservice.vo.response;

import com.example.foodiedata.model.MobileFoodFacilityModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseVO {

    public static final int STATUS_SUCCESSED = 1;
    public static final int STATUS_FAILED = 0;

    private int status;
    private List<MobileFoodFacilityModel> foodTruckList;
    private int showDistance;
    private String msg;

    public static ResponseVO BadResponse(String msg, int status) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setMsg(msg);
        responseVO.setStatus(status);

        return responseVO;
    }
}
