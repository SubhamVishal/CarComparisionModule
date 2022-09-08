package com.intuit.car.comparison.model;

import lombok.Builder;

import java.util.List;

@lombok.Data
@Builder
public class Car implements Cloneable {
    private String id;
    private CarType carType;
    private String serviceableCountry;
    private String make;
    private String model;
    private String version;
    private long year;
    private Pricing pricing;
    private List<String> availableColor;
    private Specifications specifications;
    private Features features;
    private String thumbnailPicPath;
    private List<Image> images;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}