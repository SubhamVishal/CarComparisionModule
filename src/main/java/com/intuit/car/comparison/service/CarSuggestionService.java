package com.intuit.car.comparison.service;

import com.intuit.car.comparison.config.ModuleConfig;
import com.intuit.car.comparison.exception.CarNotFoundException;
import com.intuit.car.comparison.model.Car;
import com.intuit.car.comparison.store.CarStore;
import com.intuit.car.comparison.strategy.CarClusterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CarSuggestionService {

    @Autowired
    @Qualifier("CarClusterStrategyByPriceAndType")
    private CarClusterStrategy carClusterStrategy;

    @Autowired
    private ModuleConfig config;

    public List<Car> getCarSuggestions(String carId) throws CarNotFoundException {
        Car car = CarStore.getInstance().getCarById(carId);
        if(car == null) {
            throw new CarNotFoundException();
        } 
        List<String> listing = carClusterStrategy.createOrGetCarCluster(car);
        List<Car> suggestionResult = new ArrayList<>();
        for(String id : listing) {
            if(Objects.equals(id, carId)) {
                continue;
            }
            Car currCar = CarStore.getInstance().getCarById(carId);
            if(currCar == null) {
                continue;
            }
            suggestionResult.add(currCar);
        }
        return suggestionResult.size() > config.getSuggestionSize() ? suggestionResult.subList(0, config.getSuggestionSize()) :suggestionResult;
    }
}