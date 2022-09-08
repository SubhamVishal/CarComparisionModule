package com.intuit.car.comparison.model.feature.brakingtraction;

import com.intuit.car.comparison.model.feature.FeatureBase;

@lombok.Getter
public enum BrakingTractionType {
    BRAKING_TRACTION_TYPE_1("name", "description"), BRAKING_TRACTION_TYPE_2("name", "description");
    private final String description;
    private final String name;

    BrakingTractionType(String name, String description) {
        this.description = description;
        this.name = name;
    }
}
