package com.intuit.car.comparison.model.specifications;

@lombok.Data
public class Engine {
    private String size;
    private int cylinder;
    private String engineType;
    private String engineDescription;
    private String fuelType;
    private String maxPower;
    private String maxTorque;
    private String mileageKmpl;
    private int drivingRangeKM;
    private String driveTrain;
    private String transmision;
    private String emissionStandard;
}