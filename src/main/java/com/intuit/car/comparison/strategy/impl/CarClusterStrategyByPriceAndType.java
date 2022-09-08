package com.intuit.car.comparison.strategy.impl;

import com.intuit.car.comparison.config.ModuleConfig;
import com.intuit.car.comparison.model.Car;
import com.intuit.car.comparison.model.CarType;
import com.intuit.car.comparison.model.Currency;
import com.intuit.car.comparison.model.Pricing;
import com.intuit.car.comparison.store.CarStore;
import com.intuit.car.comparison.store.CarStoreCache;
import com.intuit.car.comparison.strategy.CarClusterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("CarClusterStrategyByPriceAndType")
public class CarClusterStrategyByPriceAndType implements CarClusterStrategy {

    @Autowired
    private ModuleConfig config;

    public List<String> createOrGetCarCluster(Car car) {
        Set<String> resultCluster = CarStoreCache.getInstance().getCarCluster(car.getId());
        if (resultCluster != null && resultCluster.size() > 0) {
            return new ArrayList<>(resultCluster);
        }

        Pricing pricing = car.getPricing();
        Currency currency = pricing.getCurrency();
        double exShowroomPrice = pricing.getExShowroomPrice();
        CarType carType = car.getCarType();
        String country = car.getServiceableCountry();
        List<Car> similarCars = CarStore.getInstance().getCarsInPriceRangeAndCurrencyAndServiceableCountry(Math.abs(exShowroomPrice), Math.abs(exShowroomPrice), currency, country);

        PriorityQueue<Car> similarCarsPriorityQueue = new PriorityQueue<>((car1, car2) -> {
            if (Math.abs(exShowroomPrice - car1.getPricing().getExShowroomPrice()) < Math.abs(exShowroomPrice - car2.getPricing().getExShowroomPrice())) {
                return 1;
            } else if (Math.abs(exShowroomPrice - car1.getPricing().getExShowroomPrice()) > Math.abs(exShowroomPrice - car2.getPricing().getExShowroomPrice())) {
                return -1;
            } else {
                CarType carOneType = car1.getCarType();
                CarType carTwoType = car1.getCarType();
                if (carOneType == carTwoType) {
                    return 0;
                } else if (carOneType == carType) {
                    return 1;
                } else if (carTwoType == carType) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        for (Car similarCar : similarCars) {
            if (similarCar.getId().equals(car.getId())) {
                continue;
            }
            similarCarsPriorityQueue.add(similarCar);
        }

        int clusterSize = config.getCarClusterSize();
        while (resultCluster != null && resultCluster.size() < clusterSize && !similarCarsPriorityQueue.isEmpty()) {
            resultCluster.add(similarCarsPriorityQueue.poll().getId());
        }
        if (resultCluster != null) {
            CarStoreCache.getInstance().putClusterToCache(car.getId(), new HashSet<>(resultCluster), 60 * 60 * 1000);
            return new ArrayList<>(resultCluster);
        }
        return new ArrayList<>();
    }
}
