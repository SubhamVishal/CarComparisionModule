package com.intuit.car.comparison.model;


import com.intuit.car.comparison.model.specifications.*;
import lombok.Builder;

import java.util.List;

@lombok.Data
@Builder
public class Specifications {
    private Engine engine;
    private List<Brake> brakes;
    private Dimension dimension;
    private Capacity capacity;
    private List<Suspension> suspension;
    private Steering steering;
    private List<Wheel> wheels;
    private List<Tyre> tyres;
}