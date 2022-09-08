package com.intuit.car.comparison.model;

import com.intuit.car.comparison.model.feature.*;
import lombok.Builder;

import java.util.EnumMap;

@lombok.Data
@Builder
public class Features {
    public EnumMap<FeatureType, FeatureBase> featureMap;
}