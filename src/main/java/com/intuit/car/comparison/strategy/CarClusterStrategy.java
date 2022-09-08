package com.intuit.car.comparison.strategy;

import com.intuit.car.comparison.model.Car;

import java.util.List;

public interface CarClusterStrategy {
    List<String> createOrGetCarCluster(Car car);
}