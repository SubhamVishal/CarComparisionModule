package com.intuit.car.comparison.service;

import com.intuit.car.comparison.exception.CarAlreadyPresentException;
import com.intuit.car.comparison.exception.CarNotFoundException;
import com.intuit.car.comparison.model.Car;
import com.intuit.car.comparison.store.CarStore;
import org.springframework.stereotype.Component;

@Component
public class CarService {
    public Boolean addCar(Car car) throws CarAlreadyPresentException {
        if (car == null) {
            return false;
        }
        return CarStore.getInstance().addCar(car);
    }

    public Boolean updateCar(Car car) throws CarNotFoundException {
        if (car == null) {
            return false;
        }
        return CarStore.getInstance().updateCar(car);
    }

    public Boolean removeCar(String carId) throws CarNotFoundException {
        return CarStore.getInstance().removeCar(carId);
    }
}
