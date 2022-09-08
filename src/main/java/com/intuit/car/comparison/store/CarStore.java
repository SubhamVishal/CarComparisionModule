package com.intuit.car.comparison.store;

import java.util.*;

import com.intuit.car.comparison.exception.CarAlreadyPresentException;
import com.intuit.car.comparison.exception.CarNotFoundException;
import com.intuit.car.comparison.model.Car;
import com.intuit.car.comparison.model.Currency;

public class CarStore {
    //can be country specific maps similar to countryToPriceToCarIdMap
    private final Map<String, Car> carMap = new HashMap<>();

    private final Map<String, Map<Double, Set<String>>> countryToPriceToCarIdMap = new HashMap<>();

    private CarStore() {

    }

    private static final class InstanceHolder {
        static final CarStore INSTANCE = new CarStore();
    }

    public static CarStore getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public boolean addCar(Car car) throws CarAlreadyPresentException {
        if (this.carMap.containsKey(car.getId())) {
            throw new CarAlreadyPresentException();
        }
        this.carMap.put(car.getId(), car);
        populateCountryPriceIndex(car);
        return true;
    }

    public boolean updateCar(Car car) throws CarNotFoundException {
        if (!this.carMap.containsKey(car.getId())) {
            throw new CarNotFoundException();
        }
        Car oldCar = this.carMap.get(car.getId());
        if(oldCar.getPricing().getExShowroomPrice() != car.getPricing().getExShowroomPrice()) {
            removeCarIdFromCountryPriceIndex(oldCar);
        }
        this.carMap.put(car.getId(), car);
        populateCountryPriceIndex(car);
        return true;
    }

    public boolean removeCar(String carId) throws CarNotFoundException {
        if (!this.carMap.containsKey(carId)) {
            throw new CarNotFoundException();
        }
        Car car = this.carMap.get(carId);
        removeCarIdFromCountryPriceIndex(car);
        this.carMap.remove(carId);
        return true;
    }

    public List<Car> getCarsById(Set<String> carIdsToCompare) {
        List<Car> result = new ArrayList<>();
        for (String carId : carIdsToCompare) {
            Car car = this.getCarById(carId);
            if (car != null) {
                result.add(car);
            }
        }
        return result;
    }

    public Car getCarById(String carId) {
        return this.carMap.getOrDefault(carId, null);
    }

    public List<Car> getCarsInPriceRangeAndCurrencyAndServiceableCountry(double startPrice, double endingPrice, Currency currency, String country) {
        List<Car> result = new ArrayList<>();
        if(this.countryToPriceToCarIdMap.containsKey(country)) {
            TreeMap<Double, Set<String>> sortedPriceForCountry = (TreeMap<Double, Set<String>>) this.countryToPriceToCarIdMap.get(country);
            for(Double exShowroomPrice : sortedPriceForCountry.keySet()) {
                if(exShowroomPrice > endingPrice) {
                    break;
                }
                if(exShowroomPrice >= startPrice) {
                    Set<String> carIds = this.countryToPriceToCarIdMap.get(country).get(exShowroomPrice);
                    result.addAll(getCarsById(carIds));
                }
            }
        }
        return result;
    }

    private void populateCountryPriceIndex(Car car) {
        String country = car.getServiceableCountry();
        if (!this.countryToPriceToCarIdMap.containsKey(country)) {
            this.countryToPriceToCarIdMap.put(country, new TreeMap<>(Double::compare));
        }
        Double exShowroomPrice = car.getPricing().getExShowroomPrice();
        if (!countryToPriceToCarIdMap.get(country).containsKey(exShowroomPrice)) {
            this.countryToPriceToCarIdMap.get(country).put(exShowroomPrice, new HashSet<>());
        }
        this.countryToPriceToCarIdMap.get(country).get(exShowroomPrice).add(car.getId());
    }

    private void removeCarIdFromCountryPriceIndex(Car car) {
        String country = car.getServiceableCountry();
        if (!this.countryToPriceToCarIdMap.containsKey(country)) {
            return;
        }
        Double exShowroomPrice = car.getPricing().getExShowroomPrice();
        if (!countryToPriceToCarIdMap.get(country).containsKey(exShowroomPrice)) {
            return;
        }
        this.countryToPriceToCarIdMap.get(country).get(exShowroomPrice).remove(car.getId());
    }
}
