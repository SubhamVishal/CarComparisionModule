package com.intuit.car.comparison.model.feature.door;

@lombok.Getter
public enum DoorType {
    DOOR_TYPE_1("name","Description"), DOOR_TYPE_2("name","Description");
    private final String description;
    private final String name;
    DoorType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
