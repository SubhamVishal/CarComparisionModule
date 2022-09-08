package com.intuit.car.comparison.service;

import java.util.*;
import java.util.stream.Collectors;

import com.intuit.car.comparison.model.feature.FeatureBase;
import com.intuit.car.comparison.store.CarStore;
import com.intuit.car.comparison.model.Car;
import com.intuit.car.comparison.model.feature.FeatureType;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.stereotype.Component;

@Component
public class CarComparisonService {

    public List<Car> getCarFeaturesAndSpecifications(String carId, Set<String> carIdsToCompare, boolean onlyDifference) {
        if (carIdsToCompare.size() > 2) {
            throw new UnsupportedOperationException();
        }
        carIdsToCompare.add(carId);
        List<Car> cars = CarStore.getInstance().getCarsById(carIdsToCompare);

        List<Car> clonedCarList = new ArrayList<>();
        for (Car car : cars) {
            try {
                clonedCarList.add((Car) car.clone());
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new InternalException("Error Occurred Internally");
            }
        }
        if (!onlyDifference) {
            return clonedCarList;
        }
        Car viewedCar = clonedCarList.stream().filter(car -> car.getId().equals(car)).findFirst().get();
        List<Car> otherCars = clonedCarList.stream().filter(car -> !car.getId().equals(car)).collect(Collectors.toList());
        filterSimilarFeatures(viewedCar, otherCars);
        return otherCars;
    }

    private void filterSimilarFeatures(Car car, List<Car> otherCars) {
        Set<FeatureType> similarFeatureSet = new HashSet<>();
        for (Car otherCar : otherCars) {
            collectSimilarFeatures(car, otherCar, similarFeatureSet);
        }
        otherCars.add(car);
        for (Car currCar : otherCars) {
            for (FeatureType featureType : similarFeatureSet) {
                currCar.getFeatures().getFeatureMap().remove(featureType);
            }
        }
    }

    private void collectSimilarFeatures(Car car, Car otherCar, Set<FeatureType> similarFeatureSet) {
        EnumMap<FeatureType, FeatureBase> featureMap = car.getFeatures().getFeatureMap();
        EnumMap<FeatureType, FeatureBase> otherFeatureMap = otherCar.getFeatures().getFeatureMap();
        for (Map.Entry<FeatureType, FeatureBase> entry : featureMap.entrySet()) {
            FeatureBase feature = entry.getValue();
            FeatureType featureType = entry.getKey();
            FeatureBase otherFeature = otherFeatureMap.getOrDefault(featureType, null);
            if (otherFeature == null || otherFeature.getType().equals(feature.getType())) {
                similarFeatureSet.add(featureType);
            }
        }
    }
}