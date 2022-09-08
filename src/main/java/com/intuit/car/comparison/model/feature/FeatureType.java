package com.intuit.car.comparison.model.feature;

public enum FeatureType {
    BRAKING_TRACTION("BRAKING_TRACTION"),
    DOOR("DOOR"),
    EXTERIOR("EXTERIOR"),
    INFOTAINMENT("INFOTAINMENT"),
    LIGHTING("LIGHTING"),
    LOCKS_SECURITY("LIGHTING"),
    MANUFACTURER_WARRANTY("MANUFACTURER_WARRANTY"),
    MIRROR("MIRROR"),
    SAFETY_FEATURE("SAFETY_FEATURE"),
    SAFETY_UPHOLSTERY("SAFETY_UPHOLSTERY"),
    STORAGE("STORAGE"),
    WINDOW("WINDOW"),
    WIPER("WIPER");

    private final String name;

    FeatureType(String name) {
        this.name = name;
    }
}
