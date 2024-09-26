package com.example.foodiedata.mapper;

import com.example.foodiedata.model.MobileFoodFacilityModel;
import com.example.foodiedata.querymodel.QueryMobileFoodFacilityModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class MobileFoodFacilityMapper {

    protected final Logger logger = LoggerFactory.getLogger("MobileFoodFacilityMapper");

    private static final String FILE_PATH = "csv/Mobile_Food_Facility_Permit.csv"; // csv file path

    private static final double EARTH_RADIUS = 6371.0;

    private List<MobileFoodFacilityModel> mobileFoodFacilityModelList = null;


    public List<MobileFoodFacilityModel> selectMobileFoodFacilityByCondition(QueryMobileFoodFacilityModel query) throws Exception {
        if (StringUtils.isEmpty(query.getFood()) && query.getLatitude() == null && query.getLongitude() == null) {
            return this.mobileFoodFacilityModelList;
        }

        List<MobileFoodFacilityModel> filterList = new ArrayList<>();


        for (int i = 0; i < this.mobileFoodFacilityModelList.size(); i++) {
            MobileFoodFacilityModel aFoodTruck = this.mobileFoodFacilityModelList.get(i);

            if (!StringUtils.isEmpty(query.getFood())) {
                if (!StringUtils.isEmpty(aFoodTruck.getFoodItems()) && aFoodTruck.getFoodItems().contains(query.getFood())) {
                    filterList.add(aFoodTruck);
                }
            } else if (query.getLatitude() != null && query.getLongitude() != null) {

                double distance = this.calculateDistance(
                        query.getLatitude().doubleValue(), query.getLongitude().doubleValue(),
                        aFoodTruck.getLatitude(), aFoodTruck.getLongitude());
                aFoodTruck.setDistance(distance);

                filterList.add(aFoodTruck);
            }
        }

        if (query.getLatitude() != null && query.getLongitude() != null) {
            Collections.sort(filterList, new Comparator<MobileFoodFacilityModel>() {
                @Override
                public int compare(MobileFoodFacilityModel f1, MobileFoodFacilityModel f2) {
                    return f1.getDistance() > f2.getDistance() ? 1 : -1;
                }
            });
        }

        return filterList;
    }


    /**
     * Initialize Database
     * @return List of MobileFoodFacilityModel
     * @throws Exception
     */
    public List<MobileFoodFacilityModel> initializeDatabase() throws Exception {
        List<MobileFoodFacilityModel> list = new ArrayList<>();

        InputStreamReader inputStreamReader = null;
        CSVParser parser = null;

        try {

            String resourcesPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String csvPath = resourcesPath + FILE_PATH;

            FileReader fileReader = new FileReader(ResourceUtils.getFile(csvPath));

            CSVFormat format = CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build();
            Iterable<CSVRecord> records = format.parse(fileReader);
            for (CSVRecord record : records) {

                MobileFoodFacilityModel item = new MobileFoodFacilityModel();
                item.setLocationId(StringUtils.isEmpty(record.get("locationid")) ? null : Long.valueOf(record.get("locationid")));
                item.setApplicant(record.get("Applicant"));
                item.setFacilityType(record.get("FacilityType"));
                item.setCnn(StringUtils.isEmpty(record.get("cnn")) ? null : Long.valueOf(record.get("cnn")));
                item.setLocationDescription(record.get("LocationDescription"));
                item.setAddress(record.get("Address"));

                item.setBlockLot(record.get("blocklot"));
                item.setBlock(record.get("block"));
                item.setLot(record.get("lot"));
                item.setPermit(record.get("permit"));
                item.setStatus(record.get("Status"));
                item.setFoodItems(record.get("FoodItems"));
                item.setX(StringUtils.isEmpty(record.get("X")) ? null : Double.valueOf(record.get("X")));
                item.setY(StringUtils.isEmpty(record.get("Y")) ? null : Double.valueOf(record.get("Y")));
                item.setLatitude(StringUtils.isEmpty(record.get("Latitude")) ? null : Double.parseDouble(record.get("Latitude")));
                item.setLongitude(StringUtils.isEmpty(record.get("Longitude")) ? null : Double.parseDouble(record.get("Longitude")));
                item.setSchedule(record.get("Schedule"));
                item.setDayshours(record.get("dayshours"));
                item.setNOISent(record.get("NOISent"));

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH);
                formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));

                item.setApproved(StringUtils.isEmpty(record.get("Approved")) ? null : formatter.parse(record.get("Approved")));

                item.setReceived(StringUtils.isEmpty(record.get("Received")) ? null : Integer.valueOf(record.get("Received")));
                item.setPriorPermit(StringUtils.isEmpty(record.get("PriorPermit")) ? null : Integer.parseInt(record.get("PriorPermit")));

                item.setExpirationDate(StringUtils.isEmpty(record.get("ExpirationDate")) ? null : formatter.parse(record.get("ExpirationDate")));

                item.setLocation(record.get("Location"));
                item.setFirePreventionDistricts(StringUtils.isEmpty(record.get("Fire Prevention Districts")) ? null : Integer.valueOf(record.get("Fire Prevention Districts")));
                item.setPoliceDistricts(StringUtils.isEmpty(record.get("Police Districts")) ? null : Integer.valueOf(record.get("Police Districts")));
                item.setSupervisorDistricts(StringUtils.isEmpty(record.get("Supervisor Districts")) ? null : Integer.valueOf(record.get("Supervisor Districts")));
                item.setZipCodes(StringUtils.isEmpty(record.get("Zip Codes")) ? null : Integer.valueOf(record.get("Zip Codes")));
                item.setNeighborhoods_old(StringUtils.isEmpty(record.get("Neighborhoods (old)")) ? null : Integer.valueOf(record.get("Neighborhoods (old)")));

                list.add(item);
            }

            this.mobileFoodFacilityModelList = list;

            fileReader.close();

        } catch (Exception e) {
            logger.error("Failing to read File.", e);
        }

        return list;
    }


    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
//
//        double latDistance = Math.toRadians(lat2 - lat1);
//        double lonDistance = Math.toRadians(lon2 - lon2);
//        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        return EARTH_RADIUS * c;

        // 将角度转换为弧度
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // 计算纬度和经度的差值
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // 应用Haversine公式
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance;
    }
}
