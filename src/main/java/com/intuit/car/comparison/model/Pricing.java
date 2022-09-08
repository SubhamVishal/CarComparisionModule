package com.intuit.car.comparison.model;

import lombok.Builder;

@lombok.Data
@Builder
public class Pricing {
    private double exShowroomPrice;
    private Currency currency;
}