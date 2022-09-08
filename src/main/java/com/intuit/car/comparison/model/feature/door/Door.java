package com.intuit.car.comparison.model.feature.door;

import com.intuit.car.comparison.model.feature.FeatureBase;
import com.intuit.car.comparison.model.feature.FeatureType;
import lombok.Data;

@Data
public class Door implements FeatureBase {
    private static final FeatureType featureType = FeatureType.DOOR;
    private final DoorType type = DoorType.DOOR_TYPE_1;
    @Override
    public String getType() {
        return this.type.getName();
    }

    @Override
    public String getDescription() {
        return this.type.getDescription();
    }
}
