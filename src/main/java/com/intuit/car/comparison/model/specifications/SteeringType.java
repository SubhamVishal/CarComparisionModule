package com.intuit.car.comparison.model.specifications;

@lombok.Getter
public enum SteeringType {
    ELECTRIC_POWER_ASSISTED("Power assisted (Electric)"), BATTERY_POWER_ASSISTED("Power assisted (Battery)");

    SteeringType(String name) {
    }
}