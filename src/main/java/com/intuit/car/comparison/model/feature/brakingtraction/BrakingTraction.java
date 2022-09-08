package com.intuit.car.comparison.model.feature.brakingtraction;

import com.intuit.car.comparison.model.feature.FeatureBase;
import com.intuit.car.comparison.model.feature.FeatureType;
import lombok.Data;

@Data
public class BrakingTraction implements FeatureBase {
    private static final FeatureType featureType = FeatureType.BRAKING_TRACTION;
    private final BrakingTractionType brakingTractionType = BrakingTractionType.BRAKING_TRACTION_TYPE_1;
    @Override
    public String getType() {
        return this.brakingTractionType.getName();
    }

    @Override
    public String getDescription() {
        return this.brakingTractionType.getDescription();
    }
}
