package com.example.foodiedata.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MobileFoodFacilityModel {
    private Long locationId;
    private String applicant;
    private String facilityType;
    private Long cnn;
    private String locationDescription;
    private String address;
    private String blockLot;
    private String block;
    private String lot;
    private String permit;
    private String status;
    private String foodItems;
    private Double x;
    private Double y;
    private double latitude;
    private double longitude;
    private String schedule;
    private String dayshours;
    private String NOISent;
    private Date approved;
    private Integer received;
    private int priorPermit;
    private Date expirationDate;
    private String location;
    private Integer firePreventionDistricts;
    private Integer policeDistricts;
    private Integer supervisorDistricts;
    private Integer zipCodes;
    private Integer neighborhoods_old;

    private double distance;

}
