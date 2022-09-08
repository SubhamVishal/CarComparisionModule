package com.intuit.car.comparison.model;

@lombok.Data
public class Image {
    private ImageType imageType;
    private String imagePath;
    private String label;
}